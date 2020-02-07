package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.util.Arrays;

public class split extends MapLibraryFunctionProcessor
{
    public split(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        IMapValue regex = computeInputParameter(1, context);
        if(value.isNotNull() && regex.isNotNull())
        {
            return new MapValue(this, Arrays.asList(value.getValue(String.class).split(regex.getValue(String.class))));
        }

        return value;
    }
}
