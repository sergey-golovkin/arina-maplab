package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.contexts.ValueContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class distinct_values extends MapLibraryFunctionProcessor
{
    public distinct_values(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        if(index != null)
        {
            IMapValue value = computeInputParameter(0, context);

            if (value.isNotNull()) {
                ArrayList<Object> result = new ArrayList<>();

                if (value.getValue() instanceof List) {
                    result = (ArrayList<Object>) ((List) value.getValue()).stream().distinct().collect(Collectors.toList());
                }
                if (result.size() > 0)
                    return new MapValue(this, result);
            }
        }
        return MapValue.NULL;
    }
}
