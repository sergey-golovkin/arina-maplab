package arina.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Marshall
{
    public static String xml(Object object) throws JAXBException
    {
        if(object == null)
            return null;

        JAXBContext context = JAXBContext.newInstance(object.getClass());
        javax.xml.bind.Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, false);
        StringWriter sw = new StringWriter();
        marshaller.marshal(object, sw);
        return sw.toString();
    }

    public static String json(Object object, String dateFormat) throws JsonProcessingException
    {
        if(object == null)
            return null;

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(XMLGregorianCalendar.class, new JsonDateSerializer());
        mapper.registerModule(module);

        if(StringUtils.isNotEmpty(dateFormat))
        {
            DateFormat df = new SimpleDateFormat(dateFormat);
            //df.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
            mapper.setDateFormat(df);
        }

        return mapper.writeValueAsString(object);
    }
}
