package arina.maplab.definitions.mapforce.loaders;

import arina.maplab.definitions.mapforce.definitions.IMfdComponentDefinition;
import arina.maplab.definitions.mapforce.definitions.MfdIfElseDefinition;
import arina.maplab.definitions.mapforce.model.Component;

public class MfdLoaderKind4 implements IMfdComponentDefinitionLoader
{
    @Override
    public IMfdComponentDefinition newInstance(Component c)
    {
        return new MfdIfElseDefinition();
    }
}
