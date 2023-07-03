package arina.maplab.processors.functions.nodes;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import java.util.List;
import java.util.Map;

public class remove_null_values extends MapLibraryFunctionProcessor
{
    public remove_null_values(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue object = computeInputParameter(0, context);

        return new MapValue(this, removeNullValues(object.getValue()));
    }

    public Object removeNullValues(Object value)
    {
        if(value instanceof Map)
            removeNullValuesFromMap((Map) value);
        else if(value instanceof List)
            removeNullValuesFromList((List) value);

        return value;
    }

    private void removeNullValuesFromMap(Map values)
    {
        for (Object obj : values.values())
            removeNullValues(obj);

        values.values().removeIf(val -> (null == val || (val instanceof List && ((List) val).size() == 0) || (val instanceof Map && ((Map) val).size() == 0)));
    }

    private void removeNullValuesFromList(List values)
    {
        for (Object obj : values)
            removeNullValues(obj);

        values.removeIf(val -> (null == val || (val instanceof List && ((List) val).size() == 0) || (val instanceof Map && ((Map) val).size() == 0)));
    }
}
