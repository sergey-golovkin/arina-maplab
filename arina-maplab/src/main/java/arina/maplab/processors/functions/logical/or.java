package arina.maplab.processors.functions.logical;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

public class or extends MapLibraryFunctionProcessor
{
    public or(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        for(String item : this.definition.getInputList())
        {
            IMapValue result = computeInputValue(item, context);
            if(result.isNotNull())
            {
                if(result.getValue(Boolean.class))
                    return MapValue.TRUE;
            }
        }
        return MapValue.FALSE;
    }
}
