package arina.maplab.processors.functions.nodes;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.util.ArrayList;

public class at_first_position extends MapLibraryFunctionProcessor
{
    public at_first_position(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue nodesValue = computeInputParameter(0, context);

        if(nodesValue.isNotNull())
        {
            ArrayList<Object> nodes = new ArrayList<>();
            addValue(nodes, nodesValue.getValue(), true);
            if(nodes.size() > 0)
                return nodesValue.create(nodes.get(0));
        }
        return MapValue.NULL;
    }
}
