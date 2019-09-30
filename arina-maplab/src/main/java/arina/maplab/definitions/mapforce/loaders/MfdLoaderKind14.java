package arina.maplab.definitions.mapforce.loaders;

import arina.maplab.definitions.mapforce.definitions.IMfdComponentDefinition;
import arina.maplab.definitions.mapforce.definitions.MfdXMLDefinition;
import arina.maplab.definitions.mapforce.model.Component;

public class MfdLoaderKind14 implements IMfdComponentDefinitionLoader
{
    @Override
    public IMfdComponentDefinition newInstance(Component c)
    {
            return new MfdXMLDefinition();
    }
}
