package arina.maplab.processors.functions.logical;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

public class xor extends MapLibraryFunctionProcessor
{
    public xor(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        Boolean b = null;
        for(String item : this.definition.getInputList())
        {
            IMapValue v = computeInputValue(item, context);
            if(v.isNotNull())
            {

                if(b == null)
                    b = v.getValue(Boolean.class);
                else
                    b ^= v.getValue(Boolean.class);
            }
        }
        if(b != null)
            return new MapValue(this, b);
        else
            return MapValue.NULL;
    }
}
