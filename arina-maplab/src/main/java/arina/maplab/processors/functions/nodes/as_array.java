package arina.maplab.processors.functions.nodes;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.contexts.ValueContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class as_array extends MapLibraryFunctionProcessor
{
    public as_array(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        if(index != null)
        {
            IMapValue value = computeInputParameter(0, context);

            if (value.isNotNull())
            {
                ArrayList<Object> result = new ArrayList<>();

                if (value.getValue() instanceof List)
                {
                    for (Object item : ((List) value.getValue()))
                    {
                        processValue(result, new ValueContext(context, value.create(item)));
                    }
                }
                else
                {
                    processValue(result, new ValueContext(context, value.create(value.getValue())));
                }
                if(result.size() > 0)
                    return new MapValue(this, result);
            }
        }
        return MapValue.NULL;
    }

    private Object processValue(ArrayList<Object> result, IMapContext context) throws Exception
    {
        IMapValue value2 = computeInputParameter(1, context);
        IMapValue ignoreNULL = computeInputParameter(2, context);

        if(value2.isNotNull() || (ignoreNULL.isNotNull() && ignoreNULL.getValue(Boolean.class) == false))
            return result.add(value2.getValue());
        else
            return result;
    }
}
