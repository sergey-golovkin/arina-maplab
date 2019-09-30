package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.model.Component;
import arina.maplab.processors.IMapComponentProcessor;
import java.util.Map;

public class MfdLibraryFunctionDefinition extends MfdSTDefinition
{
    private IMapComponentProcessor processor;

    @Override
    public void load(String globalPrefix, Component component, Map<String, IMapComponentDefinition> connectorsMap) throws Exception
    {
        super.load(globalPrefix, component, connectorsMap);

        String library = "arina.maplab.processors.functions." + component.getLibrary().replaceAll("[^a-zA-Z0-9]", "_").toLowerCase();
        String name = component.getName().replaceAll("[^a-zA-Z0-9]", "_").toLowerCase();
        processor = (IMapComponentProcessor) Class.forName(library + "." + name).getConstructor(IMapComponentDefinition.class, Integer.class).newInstance(this, component.getGrowable());
    }

    @Override
    public IMapComponentProcessor getProcessor() throws Exception
    {
        return processor;
    }
}