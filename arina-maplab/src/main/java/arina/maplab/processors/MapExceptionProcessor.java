package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.value.IMapValue;

public class MapExceptionProcessor extends MapComponentProcessor
{
    public MapExceptionProcessor(IMapComponentDefinition definition)
    {
        super(definition);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        return null;
    }
}