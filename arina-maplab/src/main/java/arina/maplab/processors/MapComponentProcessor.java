package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.definitions.MfdModel;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.contexts.IMapProcessorContext;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class MapComponentProcessor implements IMapComponentProcessor
{
	protected final IMapComponentDefinition definition;
	private final Map<String, IMapValue> stackTraceValues = new HashMap<>();

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
	    IMapValue value;

        if(MfdModel.isEmptyIndex(input))
        {
        	value = MapValue.NULL;
        }
        else
        {
            IMapProcessorContext mpc = context.getContext(IMapProcessorContext.class);
            String output = definition.getLink(input).getKey();
            IMapComponentProcessor outputComponent = mpc.getProcessors().get(output);
            value = outputComponent.getValue(output, context);
        }
	    stackTraceValues.put(input, value);
	    return value;
    }

    @Override
    public void fillStackTrace(List<String> stackTrace, IMapContext context, int level) throws Exception
    {
    	int i = 0;
        for(String input : definition.getInputList())
        {
        	IMapValue value = stackTraceValues.get(input);
        	if(value != null)
	        {
		        stackTrace.add(StringUtils.repeat("\t", level) + "id: " + definition.getId() + "\tname: " + definition.getName() + "\tparam: " + i + "\tvalue: " + value.getValue(String.class) + (value.isNotNull() ? "\ttype: " + value.getValue().getClass().getTypeName() : ""));

		        if (!MfdModel.isEmptyIndex(input))
		        {
			        IMapProcessorContext mpc = context.getContext(IMapProcessorContext.class);
			        String output = definition.getLink(input).getKey();
			        IMapComponentProcessor outputComponent = mpc.getProcessors().get(output);
			        outputComponent.fillStackTrace(stackTrace, context, level + 1);
		        }
	        }
	        i++;
        }
    }
}
