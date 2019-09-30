package arina.maplab.processors.functions.nodes;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import java.util.List;

public class at_position extends MapLibraryFunctionProcessor
{
    public at_position(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue values = computeInputParameter(0, context);
        IMapValue position = computeInputParameter(1, context);

        if(values.isNotNull() && position.isNotNull())
        {
            int pos = position.getValue(Integer.class);
            if(pos >= 0)
            {
                if (values.getValue() instanceof List)
                {
                    List list = values.getValue(List.class);
                    if (list.size() > pos)
                        return values.create(list.get(position.getValue(Integer.class)));
                }
                else if (pos == 0)
                    return values;
            }
        }
        return MapValue.NULL;
    }
}
