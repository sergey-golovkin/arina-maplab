package arina.maplab.processors.functions.nodes;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.contexts.ValueContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.math.BigDecimal;
import java.util.List;

public class sum extends MapLibraryFunctionProcessor
{
    public sum(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        if(index != null)
        {
            IMapValue value = computeInputParameter(0, context);
            BigDecimal result = new BigDecimal(0);

            if (value.isNotNull())
            {
                if (value.getValue() instanceof List)
                {
                    for (Object item : ((List) value.getValue()))
                    {
                        result = processValue(result, new ValueContext(context, value.create(item)));
                    }
                }
                else
                {
                    result = processValue(result, new ValueContext(context, value.create(value.getValue())));
                }
                return new MapValue(this, result);
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
