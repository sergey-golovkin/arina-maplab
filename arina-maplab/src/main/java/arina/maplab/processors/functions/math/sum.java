package arina.maplab.processors.functions.math;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.math.BigDecimal;

public class sum extends MapLibraryFunctionProcessor
{
    public sum(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        BigDecimal result = new BigDecimal(0);
        for(String item : this.definition.getInputList())
        {
            IMapValue value = computeInputValue(item, context);
            if(value.isNotNull())
            {
                result = result.add(value.getValue(BigDecimal.class));
            }
        }
        return new MapValue(this, result);
    }
}
