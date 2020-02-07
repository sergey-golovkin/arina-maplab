package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

public class trim extends MapLibraryFunctionProcessor
{
    public trim(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        if(value.isNotNull())
        {
            String str = value.getValue(String.class);
            str = str.trim();
            if(str.length() == 0)
                return MapValue.NULL;
            else
                return new MapValue(this, str);
        }

        return MapValue.NULL;
    }
}
