package arina.utils;

import arina.maplab.value.IMapValue;

import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.util.*;

public class TypesUtils
{
    private static HashMap<String, Class> mapTypeName2Class = new HashMap<>();
    private static HashMap<Class, String> mapClass2typeName = new HashMap<>();

    static
    {
        mapTypeName2Class.put("anyType",        Object.class);
        mapTypeName2Class.put("anySimpleType",  Object.class);
        mapTypeName2Class.put("string",         String.class);
        mapTypeName2Class.put("anyURI",         String.class);
        mapTypeName2Class.put("boolean",        Boolean.class);
        mapTypeName2Class.put("float",          Float.class);
        mapTypeName2Class.put("decimal",        BigDecimal.class);
        mapTypeName2Class.put("integer",        BigInteger.class);
        mapTypeName2Class.put("long",           Long.class);
        mapTypeName2Class.put("unsignedInt",    Long.class);
        mapTypeName2Class.put("int",            Integer.class);
        mapTypeName2Class.put("unsignedShort",  Integer.class);
        mapTypeName2Class.put("short",          Short.class);
        mapTypeName2Class.put("unsignedByte",   Short.class);
        mapTypeName2Class.put("byte",           Byte.class);
        mapTypeName2Class.put("double",         Double.class);
        mapTypeName2Class.put("number",         BigDecimal.class);
        mapTypeName2Class.put("QName",          QName.class);
        mapTypeName2Class.put("NOTATION",       QName.class);
        mapTypeName2Class.put("dateTime",       XMLGregorianCalendar.class);
        mapTypeName2Class.put("date",           XMLGregorianCalendar.class);
        mapTypeName2Class.put("time",           XMLGregorianCalendar.class);
        mapTypeName2Class.put("duration",       Duration.class);
        mapTypeName2Class.put("base64Binary",   byte[].class);

        mapClass2typeName.put(Object.class, "anySimpleType");
        mapClass2typeName.put(String.class, "string");
        mapClass2typeName.put(boolean.class, "boolean");
        mapClass2typeName.put(Boolean.class, "boolean");
        mapClass2typeName.put(float.class, "float");
        mapClass2typeName.put(Float.class, "float");
        mapClass2typeName.put(BigDecimal.class, "decimal");
        mapClass2typeName.put(BigInteger.class, "integer");
        mapClass2typeName.put(long.class, "long");
        mapClass2typeName.put(Long.class, "long");
        mapClass2typeName.put(int.class, "int");
        mapClass2typeName.put(Integer.class, "int");
        mapClass2typeName.put(short.class, "short");
        mapClass2typeName.put(Short.class, "short");
        mapClass2typeName.put(byte.class, "byte");
        mapClass2typeName.put(Byte.class, "byte");
        mapClass2typeName.put(double.class, "double");
        mapClass2typeName.put(Double.class, "double");
        mapClass2typeName.put(QName.class, "QName");
        mapClass2typeName.put(XMLGregorianCalendar.class, "dateTime");
        mapClass2typeName.put(Duration.class, "duration");
        mapClass2typeName.put(byte[].class, "base64Binary");
    }

    public static boolean isSimpleType(Class clazz)
    {
        return clazz.isEnum() || mapTypeName2Class.containsValue(clazz);
    }

    public static Class getClass(String typeName) throws ClassNotFoundException
    {
        Class clazz = mapTypeName2Class.get(typeName);
        if(clazz != null)
            return clazz;
        else
            throw new ClassNotFoundException(typeName);
    }

    public static String getTypeName(Class clazz) throws ClassNotFoundException
    {
        String typeName = mapClass2typeName.get(clazz);
        if(typeName != null)
            return typeName;
        else
            throw new ClassNotFoundException(clazz.getTypeName());
    }

    public static Object getValue(String typeName, Object value) throws Exception
    {
        if (value != null && ! TypesUtils.getClass(typeName).isAssignableFrom(value.getClass()))
            return TypesUtils.getValue(typeName, TypesUtils.toString(value));
        else
            return value;
    }

    public static <T> T getValue(Class<T> clazz, Object value) throws Exception
    {
        if (value != null && ! clazz.isAssignableFrom(value.getClass()))
        {
            if(clazz.isEnum())
                return (T) clazz.getMethod("valueOf", java.lang.String.class).invoke(clazz, TypesUtils.toString(value));
            else
                return (T) TypesUtils.getValue(TypesUtils.getTypeName(clazz), TypesUtils.toString(value));
        }
        else
            return (T) value;
    }

    public static Object getValue(String typeName, String value) throws DatatypeConfigurationException, ClassNotFoundException
    {
        if(value == null)
            return null;
        if("anyType".equals(typeName))
            return DatatypeConverter.parseAnySimpleType(value);
        if("anySimpleType".equals(typeName))
            return DatatypeConverter.parseAnySimpleType(value);
        if("string".equals(typeName))
            return value;
        if("anyURI".equals(typeName))
            return value;
        if("boolean".equals(typeName))
            return DatatypeConverter.parseBoolean(value);
        if("float".equals(typeName))
            return DatatypeConverter.parseFloat(value);
        if("decimal".equals(typeName))
            return DatatypeConverter.parseDecimal(value);
        if("integer".equals(typeName))
            return BigInteger.valueOf(DatatypeConverter.parseDecimal(value).longValue());
        if("long".equals(typeName))
            return DatatypeConverter.parseDecimal(value).longValue();
        if("unsignedInt".equals(typeName))
            return DatatypeConverter.parseDecimal(value).longValue();
        if("int".equals(typeName))
            return DatatypeConverter.parseDecimal(value).intValue();
        if("unsignedShort".equals(typeName))
            return DatatypeConverter.parseDecimal(value).intValue();
        if("short".equals(typeName))
            return DatatypeConverter.parseDecimal(value).shortValue();
        if("unsignedByte".equals(typeName))
            return DatatypeConverter.parseDecimal(value).shortValue();
        if("byte".equals(typeName))
            return DatatypeConverter.parseByte(value);
        if("double".equals(typeName))
            return DatatypeConverter.parseDouble(value);
        if("number".equals(typeName))
            return DatatypeConverter.parseDecimal(value);
        if("QName".equals(typeName))
            return DatatypeConverter.parseQName(value, null);
        if("NOTATION".equals(typeName))
            return DatatypeConverter.parseQName(value, null);
        if("dateTime".equals(typeName))
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(value);
        if("date".equals(typeName))
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(value);
        if("time".equals(typeName))
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(value);
        if("duration".equals(typeName))
            return DatatypeFactory.newInstance().newDuration(value);
        if("base64Binary".equals(typeName))
            return DatatypeConverter.parseBase64Binary(value);
        throw new ClassNotFoundException(typeName);
    }

    public static String toString(Object value)
    {
        if(value == null)
            return null;
        if(value instanceof String)
            return (String) value;
        if(value instanceof Boolean)
            return DatatypeConverter.printBoolean((Boolean)value);
        if(value instanceof Float)
            return DatatypeConverter.printFloat((Float) value);
        if(value instanceof BigDecimal)
            return value.toString();
        if(value instanceof BigInteger)
            return value.toString();
        if(value instanceof Long)
            return value.toString();
        if(value instanceof Integer)
            return value.toString();
        if(value instanceof Short)
            return value.toString();
        if(value instanceof Byte)
            return value.toString();
        if(value instanceof Double)
            return DatatypeConverter.printDouble((Double) value);
        if(value instanceof QName)
            return DatatypeConverter.printQName((QName) value, null);
        if(value instanceof Calendar)
            return DatatypeConverter.printDateTime((Calendar) value);
        if(value instanceof Date)
        {
            Calendar c = Calendar.getInstance();
            c.setTime((Date) value);
            return DatatypeConverter.printDate(c);
        }
        if(value instanceof Time)
        {
                Calendar c = Calendar.getInstance();
                c.setTime((Time) value);
                return DatatypeConverter.printTime(c);
        }
        if(value instanceof Duration)
            return value.toString();
        if(value instanceof byte[])
            return DatatypeConverter.printBase64Binary((byte[]) value);

        return value.toString();
    }
}
