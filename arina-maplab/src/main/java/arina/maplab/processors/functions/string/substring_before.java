package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import org.apache.commons.lang.StringUtils;

public class substring_before extends MapLibraryFunctionProcessor
{
    public substring_before(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        IMapValue subStr = computeInputParameter(1, context);
        if(value.isNotNull() && subStr.isNotNull())
        {
            String str = value.getValue(String.class);
            String sub = subStr.getValue(String.class);

            return new MapValue(this, StringUtils.substringBefore(str, sub).trim());
        }

        return MapValue.NULL;
    }
}
