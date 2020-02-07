package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

public class matches extends MapLibraryFunctionProcessor
{
    public matches(IMapComponentDefinition definition, Integer growable)
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
            if(value.getValue(String.class).matches(regex.getValue(String.class)))
                return MapValue.TRUE;
        }

        return MapValue.FALSE;
    }
}
