package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

public class replace extends MapLibraryFunctionProcessor
{
    public replace(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        IMapValue regex = computeInputParameter(1, context);
        IMapValue replacement = computeInputParameter(2, context);
        if(value.isNotNull() && regex.isNotNull())
            return new MapValue(this, value.getValue(String.class).replaceAll(regex.getValue(String.class),  replacement.isNotNull() ? replacement.getValue(String.class) : ""));

        return MapValue.NULL;
    }
}
