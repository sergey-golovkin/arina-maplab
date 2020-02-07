package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.value.IMapValue;

public class MapIfElseProcessor extends MapComponentProcessor
{
    public MapIfElseProcessor(IMapComponentDefinition definition)
    {
        super(definition);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        for (int i = 0; i < definition.getInputList().size() - 1; i += 2)
        {
            IMapValue condition = computeInputValue(definition.getInputList().get(i), context);
            Boolean conditionValue = condition.getValue(Boolean.class);

            if (conditionValue != null && conditionValue)
                return computeInputValue(definition.getInputList().get(i + 1), context);
        }

        return computeInputValue(definition.getInputList().get(definition.getInputList().size() - 1), context);
    }
}