package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.model.Component;
import arina.maplab.processors.IMapComponentProcessor;
import arina.maplab.processors.MapOutputProcessor;
import java.util.Map;

public class MfdOutputDefinition extends MfdSTDefinition
{
    IMapComponentProcessor processor;

    @Override
    public void load(String globalPrefix, Component component, Map<String, IMapComponentDefinition> connectorsMap) throws Exception
    {
        super.load(globalPrefix, component, connectorsMap);

        connectorsMap.put(id + "|" + component.getData().getParameter().getName(), this);
        connectorsMap.put(globalPrefix + component.getData().getParameter().getName(), this);
        outputList.add(globalPrefix + name);
        processor = new MapOutputProcessor(this, component.getData().getOutput().getDatatype().value(), component.getData().getParameter().getUsageKind());
    }

    @Override
    public IMapComponentProcessor getProcessor() throws Exception
    {
        return processor;
    }

    @Override
    public boolean isOutput()
    {
        return true;
    }

}