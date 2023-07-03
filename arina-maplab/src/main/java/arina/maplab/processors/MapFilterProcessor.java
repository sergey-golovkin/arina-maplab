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

            if(value.isNotNull())
            {
                List<Object> resultTrue = new ArrayList<>();
                List<Object> resultFalse = new ArrayList<>();

                ArrayList<Object> values = new ArrayList<>();
                addValue(values, value.getValue(), true);

                for(Object item : values)
                {
                    IMapValue condition = computeInputParameter(1, new ValueContext(context, value.create(item)));
                    if(condition.isNotNull())
                    {
                        Boolean conditionValue = condition.getValue(Boolean.class);

                        if (conditionValue)
                            resultTrue.add(item);
                        else
                            resultFalse.add(item);
                    }
                }

                if(index.equals(definition.getOutputList().get(0)))
                    return value.create(resultTrue);

                if(index.equals(definition.getOutputList().get(1)))
                    return value.create(resultFalse);
            }
        }
        return MapValue.NULL;
    }
}