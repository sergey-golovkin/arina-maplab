package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.contexts.IMapProcessorContext;
import arina.maplab.processors.contexts.InputValueContext;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import java.util.HashMap;
import java.util.Map;

public class MapUserFunctionProcessor extends MapComponentProcessor
{
    final Map<String, String> inputParameters;
    final Map<String, Map.Entry<String, String>> outputParameters;

    public MapUserFunctionProcessor(IMapComponentDefinition definition, Map<String, String> inputParameters, Map<String, Map.Entry<String, String>> outputParameters)
    {
        super(definition);

        this.inputParameters = inputParameters;
        this.outputParameters = outputParameters;
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapProcessorContext mpc = context.getContext(IMapProcessorContext.class);

        IMapContext subcontext = context;
        for(Map.Entry<String, String> item : inputParameters.entrySet())
        {
            subcontext = new InputValueContext(subcontext, item.getValue(), computeInputValue(item.getKey(), context));
        }

        Map<String, IMapValue> resultValues = new HashMap<>();
        //resultValues.put(definition.getName(), mpc.getProcessors().get(outputParameters.get(index).getKey()).getValue(outputParameters.get(index).getKey(), subcontext));
        resultValues = mpc.getProcessors().get(outputParameters.get(index).getKey()).getOutputs(subcontext);
        IMapValue result = resultValues.get(outputParameters.get(index).getValue());
        if(result != null)
            return result;
        else
            return MapValue.NULL;
    }
}
