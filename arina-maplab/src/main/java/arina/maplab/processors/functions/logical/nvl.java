package arina.maplab.processors.functions.logical;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

public class nvl extends MapLibraryFunctionProcessor
{
    public nvl(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue result = MapValue.NULL;

        for(String input : definition.getInputList())
        {
            result = computeInputValue(input, context);
            if(result.isNotNull())
                return result;
        }

        return result;
    }
}
