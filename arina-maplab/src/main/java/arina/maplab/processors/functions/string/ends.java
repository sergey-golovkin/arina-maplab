package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

public class ends extends MapLibraryFunctionProcessor
{
    public ends(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        IMapValue suffix = computeInputParameter(1, context);
        IMapValue ignoreCase = computeInputParameter(2, context);
        if(value.isNotNull() && suffix.isNotNull())
        {
            if(ignoreCase.isNotNull() && ignoreCase.getValue(Boolean.class))
            {
                if (value.getValue(String.class).toUpperCase().endsWith(suffix.getValue(String.class).toUpperCase()))
                    return MapValue.TRUE;
            }
            else
                if(value.getValue(String.class).endsWith(suffix.getValue(String.class)))
                    return MapValue.TRUE;
        }

        return MapValue.FALSE;
    }
}
