package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.processors.IMapComponentProcessor;
import arina.maplab.processors.MapExceptionProcessor;

public class MfdExceptionDefinition extends MfdSTDefinition
{
    @Override
    public IMapComponentProcessor getProcessor() throws Exception
    {
        return new MapExceptionProcessor(this);
    }

    @Override
    public boolean isOutput()
    {
        return true;
    }
}