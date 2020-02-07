package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

public class concat extends MapLibraryFunctionProcessor
{
    public concat(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        for(String item : this.definition.getInputList())
        {
            IMapValue value = computeInputValue(item, context);
            if(value.isNotNull())
            {
                sb.append(value.getValue(String.class));
            }
        }

        String str = sb.toString();
        if(str == null || str.length() == 0)
            return MapValue.NULL;
        else
            return new MapValue(this, str);
    }
}
