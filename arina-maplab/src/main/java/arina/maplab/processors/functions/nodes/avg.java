package arina.maplab.processors.functions.nodes;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.contexts.ValueContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class avg extends MapLibraryFunctionProcessor
{
    public avg(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        if(index != null)
        {
            IMapValue value = computeInputParameter(0, context);
            BigDecimal result = new BigDecimal(0);
            int count = 0;

            if (value.isNotNull())
            {
                if (value.getValue() instanceof List)
                {
                    for (Object item : ((List) value.getValue()))
                    {
                        result = processValue(result, new ValueContext(context, value.create(item)));
                        count++;
                    }
                }
                else
                {
                    result = processValue(result, new ValueContext(context, value.create(value.getValue())));
                    count++;
                }
                return new MapValue(this, result.divide(new BigDecimal(count), 18, RoundingMode.UP));
            }
        }
        return MapValue.NULL;
    }

    private BigDecimal processValue(BigDecimal result, IMapContext context) throws Exception
    {
        IMapValue value2 = computeInputParameter(1, context);
        if(value2.isNotNull())
            return result.add(value2.getValue(BigDecimal.class));
        else
            return result;
    }
}
