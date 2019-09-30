package arina.maplab.processors.functions.json;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.Unmarshall;

public class unmarshal extends MapLibraryFunctionProcessor
{
    public unmarshal(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue jsonString = computeInputParameter(0, context);
        IMapValue dateFormat = computeInputParameter(1, context);

        return new MapValue(this, Unmarshall.json(jsonString.isNotNull() ? jsonString.getValue(String.class) : null, null, dateFormat.isNotNull() ? dateFormat.getValue(String.class) : null));
    }
}
