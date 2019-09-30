package arina.maplab.processors.functions.nodes;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import java.util.List;

public class at_last_position extends MapLibraryFunctionProcessor
{
    public at_last_position(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue values = computeInputParameter(0, context);

        if(values.isNotNull())
        {
            if(values.getValue() instanceof List)
            {
                List list = values.getValue(List.class);
                if(list.size() > 0)
                    return values.create(list.get(list.size() - 1));
            }
            else
                return values;
        }
        return MapValue.NULL;
    }
}
