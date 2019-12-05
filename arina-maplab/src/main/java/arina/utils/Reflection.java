package arina.utils;

import arina.maplab.definitions.mapforce.definitions.MfdVariableDefinition;
import org.apache.commons.io.FilenameUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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
                try
                {
                    method = Reflection.getMethod(object.getClass(), "setValue");
                }
                catch (NoSuchMethodException e2)
                {
                    method = Reflection.getMethod(object.getClass(), "get" + normalizeName(name));
                    Object list = method.invoke(object);
                    if (list instanceof List)
                        ((List) list).add(value);
                    return;
                }
            }
            method.invoke(object, TypesUtils.getValue(method.getParameterTypes()[0], value));
        }
    }

	public static void resetTypes(Object object, String path, Map<String, FieldDef> fields) throws Exception
	{
		if(object instanceof List)
		{
			FieldDef field = fields.get(path);
			if(field != null)
			{
				List list = (List) object;
				for (int i = 0; i < list.size(); i++)
				{
					Object item = list.get(i);

					if (item instanceof List || item instanceof Map)
						resetTypes(item, path, fields);
					else
						list.set(i, TypesUtils.getValue(field.clazz, item));
				}
			}
		}
		else if(object instanceof Map)
		{
			Map<String, Object> map = (Map) object;
			for (Map.Entry<String, Object> e : map.entrySet())
			{
				FieldDef field = fields.get(path + e.getKey());
				if (field != null) // если, тег имеет хотя бы одно соединение, то описание есть, если не подключено, то нам это не интересно.
				{
					Object o = map.get(e.getKey());
					if(field.isArray && ! (o instanceof List)) // если ожидается массив, но он содержит только одно значени, то создается не массив, а само значение.
					{                                       // поправим это
						List list = new ArrayList();
						list.add(o);
						map.put(e.getKey(), list);
					}

					if(o instanceof List) // если пришел массив
					{
						List list = (List) map.get(e.getKey());
						for(int i = 0; i < list.size(); i++)
						{
							Object item = list.get(i);

							if(field.ifValueClazz != null) // обработка тегов с аттрибутами
							{
								if(item instanceof Map) // тег представлен как Map, если есть хотя бы один атрибут,
								{
									Map map2 = (Map) item;
									if (map2.containsKey("value"))  // а само значение как элемент с именем "value"
										map2.put("value", TypesUtils.getValue(field.ifValueClazz, map2.get("value")));
								}
								else // а если атрибутов нет, то Map не создается
								{    // поправим это
									Map map2 = new LinkedHashMap<>();
									list.set(i, map2);
									map2.put("value", TypesUtils.getValue(field.ifValueClazz, item)); // а если был не Map, то значит само значени, присвоим его
								}
							}

							resetTypes(item, path + e.getKey() + "/", fields);
						}
					}
					else if(o instanceof Map) // если пришел объект или тег с атрибутами
					{
						if(field.ifValueClazz != null && ((Map)o).containsKey("value")) // тег представлен как Map, если есть хотя бы один атрибут
							((Map)o).put("value", TypesUtils.getValue(field.ifValueClazz, ((Map)o).get("value"))); // а само значение как элемент с именем "value"

						resetTypes(o, path + e.getKey() + "/", fields);
					}
					else
						map.put(e.getKey(), TypesUtils.getValue(field.clazz, o)); // это просто значение
				}
			}
		}
	}

	public static Object clone(Object value) throws IOException
	{
		return Unmarshall.json(Marshall.json(value, "yyyy-MM-dd'T'HH:mm:ss.SSSSXXX"), null, "yyyy-MM-dd'T'HH:mm:ss.SSSSXXX");
	}

	public static Map<String, FieldDef> getFieldDefs(Map<String, Object> obj, String objectType) throws Exception
	{
		Map<String, FieldDef> fields = new HashMap<>();
		fields.put("/", new FieldDef(Reflection.forName(objectType), false, false));
		loopObject(obj, "/", fields);
		return fields;
	}

	private static void loopObject(Map<String, Object> map, String path, Map<String, FieldDef> fields) throws Exception
	{
		for(Map.Entry<String, Object> entry : map.entrySet())
		{
			addFieldDef(path + entry.getKey(), fields);
			if(entry.getValue() instanceof Map)
				loopObject((Map<String, Object>) entry.getValue(), path + entry.getKey() + "/", fields);
			if(entry.getValue() instanceof ArrayList)
			{
				for (Object obj : (ArrayList) entry.getValue())
					loopObject((Map<String, Object>) obj, path + entry.getKey() + "/", fields);
			}
		}
	}

	private static void addFieldDef(String path, Map<String, FieldDef> fields) throws Exception
	{
		Class objectClass = fields.get(FilenameUtils.getFullPathNoEndSeparator(path)).clazz;
		String name = Reflection.normalizeName(FilenameUtils.getName(path));
		Method m = null;
		Class fieldClass;

		try
		{
			m = Reflection.getMethod(objectClass, "get" + name);
		}
		catch (NoSuchMethodException e2)
		{
			try
			{
				m = Reflection.getMethod(objectClass, "is" + name);
			}
			catch (NoSuchMethodException ex2)
			{
				for(Field field : objectClass.getDeclaredFields())
				{
					XmlElements annotation = field.getDeclaredAnnotation(XmlElements.class);
					if (annotation != null)
					{
						for(XmlElement element :annotation.value())
						{
							if(element.name().equals(name))
							{
								m = objectClass.getMethod("get" + Reflection.normalizeName(field.getName()));
								fieldClass = element.type();
								fields.put(path, new FieldDef(fieldClass, m.getReturnType().isAssignableFrom(List.class), false));
								return;
							}
						}
					}
				}
			}
		}

		if(m != null)
		{
			fieldClass = Reflection.forName(Reflection.normalizeType(m.getGenericReturnType().getTypeName()));
			fields.put(path, new FieldDef(fieldClass, m.getReturnType().isAssignableFrom(List.class), false));
		}
	}
}
