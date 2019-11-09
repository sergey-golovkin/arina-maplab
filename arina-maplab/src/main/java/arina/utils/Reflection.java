package arina.utils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.datatype.DatatypeConfigurationException;
import java.lang.reflect.Field;
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
    	for(int index = name.indexOf("-"); index >= 0; index = name.indexOf("-"))
        {
	        name = name.substring(0, index) + name.substring(index + 1, index + 2).toUpperCase() + name.substring(index + 2);
        }

        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static Object map2Object(Map<String, Object> object, Class clazz) throws Exception
    {
        Object root = clazz.newInstance();

        for (Map.Entry<String, Object> e : object.entrySet())
        {
            String name = e.getKey();
            Method method = null;
            Class fieldClass = null;

	        try
            {
	            method = root.getClass().getMethod("get" + Reflection.normalizeName(name));
            }
            catch (NoSuchMethodException ex)
            {
                try
                {
                    method = root.getClass().getMethod("is" + Reflection.normalizeName(name));
                }
                catch (NoSuchMethodException ex2)
                {
                    for(Field field : root.getClass().getDeclaredFields())
                    {
                        XmlElements annotation = field.getDeclaredAnnotation(XmlElements.class);
                        if (annotation != null)
                        {
                            for(XmlElement element :annotation.value())
                            {
                                if(element.name().equals(name))
                                {
                                    name = field.getName();
                                    method = root.getClass().getMethod("get" + Reflection.normalizeName(name));
                                    fieldClass = element.type();
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            if(fieldClass == null)
                fieldClass = Reflection.forName(Reflection.normalizeType(method.getGenericReturnType().getTypeName()));

            if(e.getValue() instanceof Map)
            {
                setValue(root, name, map2Object((Map<String, Object>) e.getValue(), fieldClass));
            }
            else if(e.getValue() instanceof List)
            {
                ArrayList<Object> arrayList = (ArrayList<Object>) method.invoke(root);
                for(Object item : (List) e.getValue())
                {
                    arrayList.add(map2Object((Map<String, Object>) item, fieldClass));
                }
            }
            else
            {
                if(TypesUtils.isSimpleType(fieldClass))
                    setValue(root, name, e.getValue());
                else
                {
                    Object o = fieldClass.newInstance();
                    setValue(root, name, o);
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
