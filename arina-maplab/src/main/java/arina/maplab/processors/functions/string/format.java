package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class format extends MapLibraryFunctionProcessor
{
    public format(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue format = computeInputParameter(0, context);
        Object[] params = new Object[this.definition.getInputList().size() - 1];

        for(int i = 1; i < this.definition.getInputList().size(); i++)
            params[i - 1] = computeInputParameter(i, context).getValue();

        return new MapValue(this, String.format(format.getValue(String.class), params));
    }
}
