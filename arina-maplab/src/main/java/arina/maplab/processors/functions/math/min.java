package arina.maplab.processors.functions.math;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.math.BigDecimal;

public class min extends MapLibraryFunctionProcessor
{
    public min(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        BigDecimal result = null;
        for(String item : this.definition.getInputList())
        {
            IMapValue value = computeInputValue(item, context);
            if(value.isNotNull())
            {
                if (result == null)
                    result = value.getValue(BigDecimal.class);
                else
                    result = result.min(value.getValue(BigDecimal.class));
            }
        }

        if(result == null)
            return MapValue.NULL;
        else
            return new MapValue(this, result);
    }
}
