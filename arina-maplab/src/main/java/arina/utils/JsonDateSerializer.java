package arina.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;

public class JsonDateSerializer extends JsonSerializer<XMLGregorianCalendar>
{

    @Override
    public void serialize(final XMLGregorianCalendar date, final JsonGenerator gen, final SerializerProvider provider) throws IOException
    {
        gen.writeString(date.toString());
    }
}