package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.definitions.MfdModel;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.contexts.IMapProcessorContext;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import java.util.HashMap;
import java.util.Map;

abstract public class MapComponentProcessor implements IMapComponentProcessor
{
    protected final IMapComponentDefinition definition;

    public MapComponentProcessor(IMapComponentDefinition definition)
    {
        this.definition = definition;
    }

    @Override
    public Map<String, IMapValue> getOutputs(IMapContext context) throws Exception
    {
        Map<String, IMapValue> resultValues = new HashMap<>();
        resultValues.put(definition.getName(), getValue(null, context));
        return resultValues;
    }

    protected IMapValue computeInputParameter(int parameterIndex, IMapContext context) throws Exception
    {
        return computeInputValue(definition.getInputList().get(parameterIndex), context);
    }

    protected IMapValue computeInputValue(String input, IMapContext context) throws Exception
    {
        if(MfdModel.isEmptyIndex(input))
            return MapValue.NULL;
        else
        {
            IMapProcessorContext mpc = context.getContext(IMapProcessorContext.class);
            String output = definition.getLink(input).getKey();
            IMapComponentProcessor outputComponent = mpc.getProcessors().get(output);
            return outputComponent.getValue(output, context);
        }
    }
}
