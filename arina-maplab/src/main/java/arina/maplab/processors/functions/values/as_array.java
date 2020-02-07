package arina.maplab.processors.functions.values;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.util.ArrayList;
import java.util.List;

public class as_array extends MapLibraryFunctionProcessor
{
    public as_array(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        List<Object> values = new ArrayList<>();

        for(String item : this.definition.getInputList())
            values.add(computeInputValue(item, context).getValue());

        return new MapValue(this, values);
    }
}
