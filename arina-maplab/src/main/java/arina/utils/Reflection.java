package arina.utils;

import org.apache.commons.io.FilenameUtils;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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

    public static Object newInstance(String typeName) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException
    {
        return forName(typeName).getDeclaredConstructor().newInstance();
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

		for(int index = name.indexOf("_"); index >= 0; index = name.indexOf("_"))
		{
			name = name.substring(0, index) + name.substring(index + 1, index + 2).toUpperCase() + name.substring(index + 2);
		}

        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

	public static String extractNamespace(String ns)
	{
		String namespace = "";

		for(String v : Arrays.asList(ns.replaceAll("http://www\\.|https://www\\.|http://|https://", "").replaceAll("\\{(.*)\\}.*", "$1").split("/")))
		{
			List<String> t = Arrays.asList(v.split("\\."));
			Collections.reverse(t);
			for(String s : t)
				namespace += (namespace.length() > 0 ? "." : "") + s.toLowerCase();
		}

		return namespace;
	}

	public static String extractClass(String value)
	{
		return value.replaceAll("\\{.*\\}|.*\\:(.*)", "$1");
	}

	public static Class getClass(String namespace, String name) throws ClassNotFoundException
	{
		return Reflection.forName(Reflection.extractNamespace(namespace) + "." + Reflection.normalizeName(name));
	}

	public static Class getClass(Class parent, String name) throws NoSuchMethodException, ClassNotFoundException
	{
		return Reflection.forName(Reflection.normalizeType(Reflection.getMethod(parent, "get" + Reflection.normalizeName(name)).getGenericReturnType().getTypeName()));
	}

	public static Object map2Object(Map<String, Object> object, Class clazz) throws Exception
    {
		String name = null;
		Object root = clazz.getDeclaredConstructor().newInstance();

		try
		{
			for (Map.Entry<String, Object> e : object.entrySet())
			{
				name = e.getKey();
				Method method = null;
				Class fieldClass = null;

				try
				{
					method = clazz.getMethod("get" + Reflection.normalizeName(name));
				}
				catch (NoSuchMethodException ex)
				{
					try
					{
						method = clazz.getMethod("is" + Reflection.normalizeName(name));
					}
					catch (NoSuchMethodException ex2)
					{
						for (Field field : clazz.getDeclaredFields())
						{
							XmlElements annotation = field.getDeclaredAnnotation(XmlElements.class);
							if (annotation != null)
							{
								for (XmlElement element : annotation.value())
								{
									if (element.name().equals(name))
									{
										name = field.getName();
										method = clazz.getMethod("get" + Reflection.normalizeName(name));
										fieldClass = element.type();
										break;
									}
								}
							}
						}
					}
				}

				if (method == null)
					throw new NoSuchMethodException("Method get/is" + Reflection.normalizeName(name) + " for class " + clazz.getTypeName() + " not found.");

				if (fieldClass == null)
					fieldClass = getClass(e.getValue(), Reflection.forName(Reflection.normalizeType(method.getGenericReturnType().getTypeName())), false);

				if (e.getValue() instanceof Map)
				{
					setValue(root, name, map2Object((Map<String, Object>) e.getValue(), fieldClass));
				}
				else if (e.getValue() instanceof List)
				{
					if (TypesUtils.isSimpleType(fieldClass))
					{
						for (Object item : (List) e.getValue())
						{
							setValue(root, name, TypesUtils.getValue(fieldClass, item));
						}
					}
					else
					{
						ArrayList<Object> arrayList = (ArrayList<Object>) method.invoke(root);
						if (arrayList != null)
						{
							for (Object item : (List) e.getValue())
							{
								arrayList.add(map2Object((Map<String, Object>) item, getClass(item, fieldClass, false)));
							}
						}
						else if (((List) e.getValue()).size() > 0)
						{
							Object o = fieldClass.getDeclaredConstructor().newInstance();
							setValue(root, name, o);
							for (Object item : (List) e.getValue())
							{
								for (Map.Entry<String, Object> e2 : ((Map<String, Object>)item).entrySet())
								{
									setValue(o, e2.getKey(), map2Object((Map<String, Object>) e2.getValue(),
											Reflection.forName(Reflection.normalizeType(fieldClass.getMethod("get" + Reflection.normalizeName(e2.getKey())).getGenericReturnType().getTypeName()))));
								}
							}
						}
//						else if (((List) e.getValue()).size() > 0) // если присваивают массив в не массив, такое тоже бывает.
//						{
//							setValue(root, name, map2Object((Map<String, Object>) ((List) e.getValue()).get(0), fieldClass));
//						}
					}
				}
				else
				{
					if (TypesUtils.isSimpleType(fieldClass))
						setValue(root, name, e.getValue());
					else
					{
						Object o = fieldClass.getDeclaredConstructor().newInstance();
						setValue(root, name, o);
						setValue(o, e.getKey(), e.getValue());
					}
				}
			}
		}
		catch (IllegalArgumentException e)
		{
			if (name != null)
				throw new IllegalArgumentException(e.getMessage().replaceFirst("/", '/' + name + '/'));
			else
				throw e;
		}
		catch (Exception e)
		{
			if (name != null)
				throw new IllegalArgumentException( "The field /" + name + " has invalid type.");
			else
				throw e;
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
						o = list;
					}

					if(o instanceof List) // если пришел массив
					{
						List list = (List) o;
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

							if(TypesUtils.isSimpleType(field.clazz))
								list.set(i, TypesUtils.getValue(field.clazz, item));
							else
							{
								if( ! fields.containsKey(path + e.getKey() + "[" + i + "]/"))
									enrichFields(path, e.getKey(), "[" + i + "]/", fields);

								resetTypes(item, path + e.getKey() + "[" + i + "]/", fields);
							}
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

	public static void enrichFields(String path, String key, String index, Map<String, FieldDef> fields)
	{
		for(Map.Entry<String, FieldDef> entry : new HashMap<>(fields).entrySet())
		{
			if(entry.getKey().equals(path + key))
				fields.put(path + key + index, new FieldDef(entry.getValue()));
			else if(entry.getKey().startsWith(path + key + "/"))
				fields.put(entry.getKey().replace(path + key + "/", path + key + index), new FieldDef(entry.getValue()));
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
			if(entry.getValue() instanceof Map)
			{
				addFieldDef(path + entry.getKey(), fields, entry.getValue(), -1);
				loopObject((Map<String, Object>) entry.getValue(), path + entry.getKey() + "/", fields);
			}
			else if(entry.getValue() instanceof ArrayList)
			{
				addFieldDef(path + entry.getKey(), fields, null, -1);
				ArrayList array = (ArrayList) entry.getValue();
				for(int i = 0; i < array.size(); i++)
				{
					Object obj = array.get(i);
					addFieldDef(path + entry.getKey(), fields, obj, i);
					if(obj instanceof Map)
						loopObject((Map<String, Object>) obj, path + entry.getKey() + "[" + i + "]" + "/", fields);
				}
			}
			else
				addFieldDef(path + entry.getKey(), fields, entry.getValue(), -1);
		}
	}

	private static void addFieldDef(String path, Map<String, FieldDef> fields, Object children, int index) throws Exception
	{
		Class objectClass = fields.get(FilenameUtils.getFullPathNoEndSeparator(path)).clazz;
		String name = Reflection.normalizeName(FilenameUtils.getName(path));
		Method method = null;
		Class fieldClass;

		try
		{
			method = Reflection.getMethod(objectClass, "get" + name);
		}
		catch (NoSuchMethodException e2)
		{
			try
			{
				method = Reflection.getMethod(objectClass, "is" + name);
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
								method = objectClass.getMethod("get" + Reflection.normalizeName(field.getName()));
								fieldClass = element.type();
								fields.put(path, new FieldDef(fieldClass, method.getReturnType().isAssignableFrom(List.class), false));
								return;
							}
						}
					}
				}
			}
		}

		if(method != null)
		{
			fieldClass = getClass(children, Reflection.forName(Reflection.normalizeType(method.getGenericReturnType().getTypeName())), true);
			fields.put(path + (index < 0 ? "" : "[" + index + "]"), new FieldDef(fieldClass, method.getReturnType().isAssignableFrom(List.class), false));
		}
		else
		{
			if("type".equals(FilenameUtils.getName(path)))
				fields.put(path + (index < 0 ? "" : "[" + index + "]"), new FieldDef(String.class, false, true));
		}
	}

	private static Class getClass(Object children, Class fieldClass, boolean isPutRemove) throws ClassNotFoundException
	{
		int mod = fieldClass.getModifiers();
		if(Modifier.isAbstract(mod) && ! Modifier.isInterface(mod))
		{
			String type = getType(children);
			if(type != null && type.length() > 0)
			{
				type = Reflection.extractClass(type);
				XmlSeeAlso seeAlso = ((XmlSeeAlso) fieldClass.getAnnotation(XmlSeeAlso.class));
				if(seeAlso != null)
				{
					for(Class clazz : seeAlso.value())
					{
						String alsoname = clazz.getSimpleName();
						if(alsoname.equals(type))
						{
							fieldClass = clazz;
							if(isPutRemove)
								putType(children, clazz, type);
							else
								removeType(children);
							break;
						}
					}
				}
			}
		}
		return fieldClass;
	}

	private static String getType(Object children)
	{
		if(children instanceof Map)
			return (String) ((Map) children).get("type");

		return null;
	}

	private static void putType(Object children, Class clazz, String type)
	{
		if(children instanceof Map)
			((Map)children).put("type", "{" + clazz.getPackage().getAnnotation(XmlSchema.class).namespace() + "}" + type);
	}

	private static void removeType(Object children)
	{
		if(children instanceof Map)
			((Map) children).remove("type");
	}
}
