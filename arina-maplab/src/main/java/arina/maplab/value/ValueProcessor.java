package arina.maplab.value;

import java.util.ArrayList;
import java.util.List;

public abstract class ValueProcessor
{
    public IMapValue process(IMapValue value) throws Exception
    {
        if(value.isNotNull())
            return value.create(process(value.getValue()));
        else
            return MapValue.NULL;
    }

    public Object process(Object value) throws Exception
    {
        if(value instanceof List)
        {
            ArrayList<Object> array = new ArrayList<>();

            for(Object item : (List) value)
            {
                array.add(process(item));
            }
            return array;
        }
        else
            return processValue(value);
    }

    protected abstract Object processValue(Object value) throws Exception;
}
