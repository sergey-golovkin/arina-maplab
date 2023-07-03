package arina.maplab.processors.functions.nodes;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.util.ArrayList;

public class at_position extends MapLibraryFunctionProcessor
{
    public at_position(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue nodesValue = computeInputParameter(0, context);
        IMapValue position = computeInputParameter(1, context);

        if(nodesValue.isNotNull() && position.isNotNull())
        {
            int pos = position.getValue(Integer.class);
            if(pos >= 0)
            {
                ArrayList<Object> nodes = new ArrayList<>();
                addValue(nodes, nodesValue.getValue(), true);
                if (nodes.size() > pos)
                    return nodesValue.create(nodes.get(pos));
            }
        }
        return MapValue.NULL;
    }
}
