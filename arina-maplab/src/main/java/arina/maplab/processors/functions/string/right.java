package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

public class right extends MapLibraryFunctionProcessor
{
    public right(IMapComponentDefinition definition, Integer growable)
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
            int begin = str.length() - substringLength.getValue(Integer.class);
            if(begin < 0)
                begin = 0;
            return new MapValue(this, str.substring(begin));
        }

        return MapValue.NULL;
    }
}
