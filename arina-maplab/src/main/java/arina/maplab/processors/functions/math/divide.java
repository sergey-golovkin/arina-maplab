package arina.maplab.processors.functions.math;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class divide extends MapLibraryFunctionProcessor
{
    public divide(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        IMapValue divisor = computeInputParameter(1, context);

        if(value.isNotNull() && divisor.isNotNull())
        {
            return new MapValue(this, value.getValue(BigDecimal.class).divide(divisor.getValue(BigDecimal.class), 18, RoundingMode.UP));
        }
        return MapValue.NULL;
    }
}
