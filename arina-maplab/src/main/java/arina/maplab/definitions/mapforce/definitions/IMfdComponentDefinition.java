package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.model.Component;
import java.util.Map;

public interface IMfdComponentDefinition extends IMapComponentDefinition
{
    void load(String globalPrefix, Component component, Map<String, IMapComponentDefinition> connectorsMap) throws Exception;
}
