package arina.maplab.processors.functions.math;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class round extends MapLibraryFunctionProcessor
{
    public round(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        IMapValue precision = computeInputParameter(1, context);

        if(value.isNotNull() && precision.isNotNull())
        {
            if (precision.getValue() instanceof String)
            {
                MathContext mc = new MathContext((String) precision.getValue());
                return new MapValue(this, value.getValue(BigDecimal.class).setScale(mc.getPrecision(), mc.getRoundingMode()));
            }
            if (precision.getValue() instanceof Number)
                return new MapValue(this, value.getValue(BigDecimal.class).setScale(precision.getValue(Integer.class), RoundingMode.UP));
        }
        return value;
    }
}
