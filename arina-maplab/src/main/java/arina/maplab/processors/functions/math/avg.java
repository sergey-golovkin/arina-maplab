package arina.maplab.processors.functions.math;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class avg extends MapLibraryFunctionProcessor
{
    public avg(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        BigDecimal result = new BigDecimal(0);
        int n = 0;
        for(String item : this.definition.getInputList())
        {
            IMapValue value = computeInputValue(item, context);
            if(value.isNotNull())
            {
                result = result.add(value.getValue(BigDecimal.class));
                n++;
            }
        }

        if(n > 0)
            return new MapValue(this, result.divide(new BigDecimal(n), 18, RoundingMode.UP));
        else
            return MapValue.NULL;
    }
}
