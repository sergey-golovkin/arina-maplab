package arina.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@JacksonStdImpl
public class UntypedObjectDeserializerArraySupport extends UntypedObjectDeserializer
{
    public UntypedObjectDeserializerArraySupport()
    {
        super(null, null);
    }

    String checkValue(String key)
    {
        return (key == null || key.length() == 0 ? "value" : key);
    }

    @Override
    protected Object mapObject(JsonParser p, DeserializationContext ctxt) throws IOException
    {
        String key1;

        JsonToken t = p.getCurrentToken();

        if (t == JsonToken.START_OBJECT) {
            key1 = p.nextFieldName();
        } else if (t == JsonToken.FIELD_NAME) {
            key1 = p.getCurrentName();
        } else {
            if (t != JsonToken.END_OBJECT) {
                return ctxt.handleUnexpectedToken(handledType(), p);
            }
            key1 = null;
        }
        if (key1 == null) {
            // empty map might work; but caller may want to modify... so better just give small modifiable
            return new LinkedHashMap<String,Object>(2);
        }
        // minor optimization; let's handle 1 and 2 entry cases separately
        // 24-Mar-2015, tatu: Ideally, could use one of 'nextXxx()' methods, but for
        //   that we'd need new method(s) in JsonDeserializer. So not quite yet.
        p.nextToken();
        Object value1 = deserialize(p, ctxt);

        String key2 = p.nextFieldName();
        if (key2 == null) { // has to be END_OBJECT, then
            // single entry; but we want modifiable
            LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>(2);
            result.put(checkValue(key1), value1);
            return result;
        }
        p.nextToken();
        Object value2 = deserialize(p, ctxt);

        String key = p.nextFieldName();

        if (key == null) {
            LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>(4);
            result.put(checkValue(key1), value1);
            result.put(checkValue(key2), value2);
            return result;
        }
        // And then the general case; default map size is 16
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        if(checkValue(key1).equals(checkValue(key2)))
        {
            ArrayList<Object> array = new ArrayList<>();
            result.put(checkValue(key1), array);
            array.add(value1);
            array.add(value2);
        }
        else
        {
            result.put(checkValue(key1), value1);
            result.put(checkValue(key2), value2);
        }

        do {
            p.nextToken();
            Object value3 = deserialize(p, ctxt);
            if(result.containsKey(checkValue(key)))
            {
                Object value4 = result.get(checkValue(key));
                if(value4 instanceof List)
                    ((List)value4).add(value3);
                else
                {
                    ArrayList<Object> array = new ArrayList<>();
                    array.add(value4);
                    array.add(value3);
                    result.put(checkValue(key), array);
                }
            }
            else
                result.put(checkValue(key), value3);
        } while ((key = p.nextFieldName()) != null);
        return result;
    }

}
