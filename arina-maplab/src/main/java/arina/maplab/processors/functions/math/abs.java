package arina.maplab.processors.functions.math;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;

import java.math.BigDecimal;

public class abs extends MapLibraryFunctionProcessor
{
    public abs(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        if(value.isNotNull())
        {
            return value.create(value.getValue(BigDecimal.class).abs());
        }
        return value;
    }
}
