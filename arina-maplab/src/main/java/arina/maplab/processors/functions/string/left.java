package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

public class left extends MapLibraryFunctionProcessor
{
    public left(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        IMapValue substringLength = computeInputParameter(1, context);
        if(value.isNotNull() && substringLength.isNotNull())
        {
            String str = value.getValue(String.class);
            int end = substringLength.getValue(Integer.class);
            if(end > str.length())
                end = str.length();
            return new MapValue(this, str.substring(0, end));
        }

        return MapValue.NULL;
    }
}
