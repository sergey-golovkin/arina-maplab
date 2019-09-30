package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.model.Component;
import arina.maplab.processors.IMapComponentProcessor;
import arina.maplab.processors.MapConstantProcessor;
import arina.utils.TypesUtils;
import java.util.Map;

public class MfdConstantDefinition extends MfdSTDefinition
{
    IMapComponentProcessor processor;

    @Override
    public void load(String globalPrefix, Component component, Map<String, IMapComponentDefinition> connectorsMap) throws Exception
    {
        super.load(globalPrefix, component, connectorsMap);

        processor = new MapConstantProcessor(this, TypesUtils.getValue(component.getData().getConstant().getDatatype().value(), component.getData().getConstant().getValue()));
    }

    @Override
    public IMapComponentProcessor getProcessor() throws Exception
    {
        return processor;
    }
}
