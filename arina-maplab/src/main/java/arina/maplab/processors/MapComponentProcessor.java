package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.definitions.MfdModel;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.contexts.IMapProcessorContext;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.OopsException;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class MapComponentProcessor implements IMapComponentProcessor
{
	protected final IMapComponentDefinition definition;
	protected final Map<String, IMapValue> stackTraceValues = new HashMap<>();
	private boolean stackTraceFilled = false;

    public MapComponentProcessor(IMapComponentDefinition definition)
    {
        this.definition = definition;
    }

    @Override
    public String getId()
    {
        return definition.getId();
    }

    @Override
    public String getName()
    {
        return definition.getName();
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
    	if(definition.getInputList().size() > parameterIndex)
            return computeInputValue(definition.getInputList().get(parameterIndex), context);
    	else
    		return MapValue.NULL;
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
    public void fillStackTrace(List<String> stackTrace, Map<String, String> dump, IMapContext context, int level) throws Exception
    {
    	if( ! stackTraceFilled)
        {
            stackTraceFilled = true;
            for (String input : definition.getInputList())
            {
                String key = "component \"" + definition.getName() + "\" with id: " + definition.getId() + " " + getName(input);
                IMapValue value = stackTraceValues.get(input);
                if(value != null)
                    dump.put(key, (value.isNotNull() ? value.getValue(String.class).replace("\n", "").replace("\r", "") : "NULL"));

                if (!MfdModel.isEmptyIndex(input))
                {
                    IMapProcessorContext mpc = context.getContext(IMapProcessorContext.class);
                    String output = definition.getLink(input).getKey();
                    IMapComponentProcessor outputComponent = mpc.getProcessors().get(output);
                    stackTrace.add(StringUtils.repeat("\t", level) + key + " depends from component \"" + outputComponent.getName() + "\" with id: " + outputComponent.getId() + " " + outputComponent.getName(output));
                    outputComponent.fillStackTrace(stackTrace, dump, context, level + 1);
                }
                else
                    stackTrace.add(StringUtils.repeat("\t", level) + key);
            }
        }
    }

    @Override
    public String getName(String index) throws Exception
    {
        List<String> list = definition.getInputList();
        for(int i = 0; i < list.size(); i++)
            if(list.get(i).equals(index))
                return "input parameter #: " + i;

        list = definition.getOutputList();
        for(int i = 0; i < list.size(); i++)
            if(list.get(i).equals(index))
                return "output parameter #: " + i;

        return "Unknown";
    }

    protected void throwException(String index, String message, IMapContext context, Exception e) throws Exception
    {
        List<String > stackTrace = new ArrayList<>();
        Map<String, String> dump = new HashMap<>();

        fillStackTrace(stackTrace, dump, context, 0);
        throw new OopsException("Error occured in the component \"" + definition.getName() + "\" with id: " + definition.getId() + " while computing " + this.getName(index) + " with id: " + index + "\n" + message, stackTrace, dump, e);
    }

    protected abstract IMapValue getValueInternal(String index, IMapContext context) throws Exception;

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        try
        {
            return getValueInternal(index, context);
        }
        catch (OopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throwException(index, e.getMessage(), context, e);
            return MapValue.NULL;
        }
    }
}
