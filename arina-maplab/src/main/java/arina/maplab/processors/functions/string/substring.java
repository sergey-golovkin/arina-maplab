package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

public class substring extends MapLibraryFunctionProcessor
{
    public substring(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        IMapValue beginIndex = computeInputParameter(1, context);
        IMapValue endIndex = computeInputParameter(2, context);
        if(value.isNotNull() && beginIndex.isNotNull())
        {
            if(endIndex.isNotNull())
            {
                return new MapValue(this, value.getValue(String.class).substring(beginIndex.getValue(Integer.class), endIndex.getValue(Integer.class)));
            }
            else
                return new MapValue(this, value.getValue(String.class).substring(beginIndex.getValue(Integer.class)));
        }

        return MapValue.NULL;
    }
}
