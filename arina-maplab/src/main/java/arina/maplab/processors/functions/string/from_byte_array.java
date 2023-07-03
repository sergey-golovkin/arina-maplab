package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

public class from_byte_array extends MapLibraryFunctionProcessor
{
    public from_byte_array(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        if(value.isNotNull())
        {
            IMapValue charset = computeInputParameter(1, context);
            return new MapValue(this, new String(value.getValue(byte[].class), charset.isNotNull() ? charset.getValue(String.class) : "utf8"));
        }
        return MapValue.NULL;
    }
}
