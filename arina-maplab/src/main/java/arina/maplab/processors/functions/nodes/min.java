package arina.maplab.processors.functions.nodes;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.contexts.ValueContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import org.apache.commons.lang.builder.CompareToBuilder;

import java.util.List;

public class min extends MapLibraryFunctionProcessor
{
    public min(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        if(index != null)
        {
            IMapValue value = computeInputParameter(0, context);
            Object result = null;

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

    private Object processValue(Object result, IMapContext context) throws Exception
    {
        IMapValue value2 = computeInputParameter(1, context);
        if(value2.isNotNull())
        {
            if(result == null)
                return value2.getValue();
            else
                return CompareToBuilder.reflectionCompare(result, value2.getValue()) < 0 ? result : value2.getValue();
        }
        else
            return result;
    }
}
