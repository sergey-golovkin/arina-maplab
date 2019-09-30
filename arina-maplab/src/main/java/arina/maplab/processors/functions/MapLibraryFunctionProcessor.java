package arina.maplab.processors.functions;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.MapComponentProcessor;

public abstract class MapLibraryFunctionProcessor extends MapComponentProcessor
{
    protected Integer growable;

    public MapLibraryFunctionProcessor(IMapComponentDefinition definition, Integer growable)
    {
        super(definition);
    }
}
