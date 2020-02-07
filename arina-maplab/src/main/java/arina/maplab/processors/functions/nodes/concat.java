package arina.maplab.processors.functions.nodes;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.contexts.ValueContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import java.util.List;

public class concat extends MapLibraryFunctionProcessor
{
    public concat(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        if(index != null)
        {
            IMapValue value = computeInputParameter(0, context);
            String result = null;

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
                if(result != null)
                {
                    IMapValue prefix = computeInputParameter(2, context);
                    IMapValue suffix = computeInputParameter(4, context);
                    return new MapValue(this, (prefix.isNotNull() ? prefix.getValue(String.class) : "") + result + (suffix.isNotNull() ? suffix.getValue(String.class) : ""));
                }
            }
        }
        return MapValue.NULL;
    }

    private String processValue(String result, IMapContext context) throws Exception
    {
        IMapValue string = computeInputParameter(1, context);

        if(string.isNotNull())
        {
            IMapValue delimiter = computeInputParameter(3, context);
            if(result == null)
                return string.getValue(String.class);
            else
                return result + delimiter.getValue(String.class) + string.getValue(String.class);
        }
        else
            return result;
    }
}
