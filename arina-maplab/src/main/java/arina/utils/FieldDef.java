package arina.utils;

import javax.xml.bind.annotation.XmlValue;
import java.lang.reflect.Field;
import java.util.Map;

public class FieldDef
{
	final public Class clazz;
	final public boolean isArray;
	final public Class ifValueClazz;
	final public boolean isAttribute;
	final public boolean isObject;

	public FieldDef(Class clazz, boolean isArray, boolean isAttribute)
	{
		this.clazz = clazz;
		this.isArray = isArray;
		this.isAttribute = isAttribute;
		this.isObject = clazz.isAssignableFrom(Map.class) || ! TypesUtils.isSimpleType(clazz);

		Class ifvt = null;
		for(Field f : clazz.getDeclaredFields())
		{
			if(f.getAnnotation(XmlValue.class) != null)
			{
				ifvt = f.getType();
				break;
			}
		}
		this.ifValueClazz = ifvt;
	}
}
