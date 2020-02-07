package arina.maplab.processors.functions.math;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.math.BigDecimal;

public class modulus extends MapLibraryFunctionProcessor
{
    public modulus(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue value1 = computeInputParameter(0, context);
        IMapValue value2 = computeInputParameter(1, context);

        if(value1.isNotNull() && value2.isNotNull())
        {
            return new MapValue(this, value1.getValue(BigDecimal.class).remainder(value2.getValue(BigDecimal.class)));
        }
        return MapValue.NULL;
    }
}
