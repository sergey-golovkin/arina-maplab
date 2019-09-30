package arina.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Reflection
{
    public static Method getMethod(Class clazz, String name) throws NoSuchMethodException
    {
        for (Method m : clazz.getMethods())
        {
            if(m.getName().equalsIgnoreCase(name))
                return m;
        }

        throw new NoSuchMethodException("Method not found [" + clazz.toString() + "." + name + "]");
    }

    public static Class forName(String typeName) throws ClassNotFoundException
    {
        try
        {
            return Class.forName(typeName);
        }
        catch (ClassNotFoundException e)
        {
            if (typeName.contains("."))
                throw e;

            return TypesUtils.getClass(typeName);
        }
    }

    public static Object newInstance(String typeName) throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        return forName(typeName).newInstance();
    }

    public static String normalizeType(String type)
    {
        return type.replaceAll("java.util.List<(.*)>", "$1");
    }

    public static String normalizeName(String name)
    {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static Object map2Object(Map<String, Object> object, Class clazz) throws Exception
    {
        Object root = clazz.newInstance();

        for (Map.Entry<String, Object> e : object.entrySet())
        {
            Method m = root.getClass().getMethod("get" + Reflection.normalizeName(e.getKey()));
            Class fieldClass = Reflection.forName(Reflection.normalizeType(m.getGenericReturnType().getTypeName()));
            if(e.getValue() instanceof Map)
            {
                setValue(root, e.getKey(), map2Object((Map<String, Object>) e.getValue(), fieldClass));
            }
            else if(e.getValue() instanceof List)
            {
                ArrayList<Object> arrayList = (ArrayList<Object>) m.invoke(root);
                for(Object item : (List) e.getValue())
                {
                    arrayList.add(map2Object((Map<String, Object>) item, fieldClass));
                }

            }
            else
            {
                if(TypesUtils.isSimpleType(fieldClass))
                    setValue(root, e.getKey(), e.getValue());
                else
                {
                    Object o = fieldClass.newInstance();
                    setValue(root, e.getKey(), o);
                    setValue(o, e.getKey(), e.getValue());
                }
            }
        }
        return root;
    }

    private static void setValue(Object object, String name, Object value) throws Exception
    {
        if(value != null)
        {
            Method method = null;

            try
            {
                method = Reflection.getMethod(object.getClass(), "set" + normalizeName(name));
            }
            catch (NoSuchMethodException e)
            {
                method = Reflection.getMethod(object.getClass(), "setValue");
            }

            method.invoke(object, TypesUtils.getValue(method.getParameterTypes()[0], value));
        }
    }
}
