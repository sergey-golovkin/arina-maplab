package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.model.Component;
import arina.maplab.processors.IMapComponentProcessor;
import arina.maplab.processors.MapFilterProcessor;
import arina.maplab.processors.MapIfElseProcessor;

import java.util.Map;

public class MfdIfElseDefinition extends MfdSTDefinition
{
    IMapComponentProcessor processor;

    @Override
    public void load(String globalPrefix, Component component, Map<String, IMapComponentDefinition> connectorsMap) throws Exception
    {
        super.load(globalPrefix, component, connectorsMap);

        processor = new MapIfElseProcessor(this);
    }

    @Override
    public IMapComponentProcessor getProcessor() throws Exception
    {
        return processor;
    }
}