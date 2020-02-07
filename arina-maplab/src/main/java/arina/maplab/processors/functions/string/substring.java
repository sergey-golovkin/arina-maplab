package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

public class substring extends MapLibraryFunctionProcessor
{
    public substring(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        IMapValue beginIndex = computeInputParameter(1, context);
        IMapValue substringLength = computeInputParameter(2, context);
        if(value.isNotNull() && beginIndex.isNotNull())
        {
            int begin = beginIndex.getValue(Integer.class);
            if(substringLength.isNotNull())
            {
                String str = value.getValue(String.class);
                int end = begin + substringLength.getValue(Integer.class);
                if(end > str.length())
                    end = str.length();
                return new MapValue(this, str.substring(begin, end));
            }
            else
                return new MapValue(this, value.getValue(String.class).substring(begin));
        }

        return MapValue.NULL;
    }
}
