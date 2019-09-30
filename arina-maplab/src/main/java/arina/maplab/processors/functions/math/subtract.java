package arina.maplab.processors.functions.math;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.math.BigDecimal;

public class subtract extends MapLibraryFunctionProcessor
{
    public subtract(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        IMapValue subtrahend = computeInputParameter(1, context);

        if(value.isNotNull() && subtrahend.isNotNull())
        {
            return new MapValue(this, value.getValue(BigDecimal.class).subtract(subtrahend.getValue(BigDecimal.class)));
        }
        return MapValue.NULL;
    }
}
