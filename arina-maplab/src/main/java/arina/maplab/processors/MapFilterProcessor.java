package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.contexts.ValueContext;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.util.ArrayList;
import java.util.List;

public class MapFilterProcessor extends MapComponentProcessor
{
    public MapFilterProcessor(IMapComponentDefinition definition)
    {
        super(definition);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        if(index != null)
        {
            IMapValue value = computeInputParameter(0, context);

            if (value.isNotNull())
            {
                List<Object> resultTrue = new ArrayList<>();
                List<Object> resultFalse = new ArrayList<>();

                if (value.getValue() instanceof List)
                {
                    for (Object item : ((List) value.getValue()))
                    {
                        processValue(item, new ValueContext(context, value.create(item)), resultTrue, resultFalse);
                    }
                }
                else
                {
                    processValue(value.getValue(), new ValueContext(context, value.create(value.getValue())), resultTrue, resultFalse);
                }

                if (index.equals(definition.getOutputList().get(0)))
                    return value.create(resultTrue);

                if (index.equals(definition.getOutputList().get(1)))
                    return value.create(resultFalse);
            }
        }
        return MapValue.NULL;
    }

    private void processValue(Object value, IMapContext context, List<Object> resultTrue, List<Object> resultFalse) throws Exception
    {
        IMapValue condition = computeInputParameter(1, context);
        if(condition.isNotNull())
        {
            Boolean conditionValue = condition.getValue(Boolean.class);

            if (conditionValue)
                resultTrue.add(value);
            else
                resultFalse.add(value);
        }
    }
}