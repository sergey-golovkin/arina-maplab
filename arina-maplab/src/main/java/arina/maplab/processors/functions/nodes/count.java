package arina.maplab.processors.functions.nodes;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.util.ArrayList;

public class count extends MapLibraryFunctionProcessor
{
    public count(IMapComponentDefinition definition, Integer growable)
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
                ArrayList<Object> nodes = new ArrayList<>();
                addValue(nodes, nodesValue.getValue(), true);
                return new MapValue(this, nodes.size());
            }
        }
        return MapValue.NULL;
    }
}
