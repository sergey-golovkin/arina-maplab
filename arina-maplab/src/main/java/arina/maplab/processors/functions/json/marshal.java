package arina.maplab.processors.functions.json;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.Marshall;

public class marshal extends MapLibraryFunctionProcessor
{
    public marshal(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue object = computeInputParameter(0, context);
        IMapValue dateFormat = computeInputParameter(1, context);

        return new MapValue(this, Marshall.json(object.getValue(), dateFormat.isNotNull() ? dateFormat.getValue(String.class) : null));
    }
}
