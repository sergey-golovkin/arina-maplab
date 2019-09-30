package arina.maplab.processors.functions.xml;

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
        IMapValue xmlString = computeInputParameter(0, context);

        return new MapValue(this, Unmarshall.xml(xmlString.isNotNull() ? xmlString.getValue(String.class) : null,null));
    }
}
