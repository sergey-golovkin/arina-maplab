package arina.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class Unmarshall
{
    public static Object xml(String xml, String type) throws JAXBException, IOException
    {
        if(xml == null || xml.length() == 0)
            return null;

        try
        {
            if(type != null && type.length() > 0)
            {
                Class<?> clazz = Class.forName(type);
                JAXBContext context = JAXBContext.newInstance(clazz);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                return unmarshaller.unmarshal(new StringReader(xml));
            }
        }
        catch (ClassNotFoundException ignore)
        {
        }

        XmlMapper mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Object.class, new UntypedObjectDeserializerArraySupport());
        mapper.registerModule(module);
        Object map = mapper.readValue(xml, Object.class);
        if(map instanceof Map)
        {
            ((Map) map).remove("schemaLocation");
            ((Map) map).remove("noNamespaceSchemaLocation");
        }
        return map;
    }

    public static Object json(String json, String type, String dateFormat) throws IOException
    {
        if(json == null || json.length() == 0)
            return null;

        Class<?> clazz;

        try
        {
            if (type == null || type.length() == 0)
                clazz = Object.class;
            else
                clazz = Class.forName(type);
        }
        catch (ClassNotFoundException ignore)
        {
            clazz = Object.class;
        }

        ObjectMapper mapper = new ObjectMapper();
        if(StringUtils.isNotEmpty(dateFormat))
        {
            DateFormat df = new SimpleDateFormat(dateFormat);
            //df.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
            mapper.setDateFormat(df);
        }
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json, clazz);
    }
}
