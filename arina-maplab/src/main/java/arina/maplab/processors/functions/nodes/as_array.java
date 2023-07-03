package arina.maplab.processors.functions.nodes;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.contexts.ValueContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.util.ArrayList;

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
            IMapValue nodesValue = computeInputParameter(0, context);

            if (nodesValue.isNotNull())
            {
                ArrayList<Object> result = new ArrayList<>();
                ArrayList<Object> nodes = new ArrayList<>();
                addValue(nodes, nodesValue.getValue(), true);

                for (Object item : nodes)
                    processValue(result, new ValueContext(context, nodesValue.create(item)));

                if(result.size() > 0)
                    return new MapValue(this, result);
            }
        }
        return MapValue.NULL;
    }

    private void processValue(ArrayList<Object> result, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(1, context);
        IMapValue ignoreNULL = computeInputParameter(2, context);

        addValue(result, value.getValue(), (ignoreNULL.isNotNull() && ignoreNULL.getValue(Boolean.class) == false));
    }
}
