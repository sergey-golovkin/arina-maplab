package arina.maplab.definitions.mapforce.loaders;

import arina.maplab.definitions.mapforce.definitions.IMfdComponentDefinition;
import arina.maplab.definitions.mapforce.model.Component;

public interface IMfdComponentDefinitionLoader
{
    IMfdComponentDefinition newInstance(Component c);
}
