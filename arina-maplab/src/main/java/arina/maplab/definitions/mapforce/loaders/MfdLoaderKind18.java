package arina.maplab.definitions.mapforce.loaders;

import arina.maplab.definitions.mapforce.definitions.IMfdComponentDefinition;
import arina.maplab.definitions.mapforce.definitions.MfdExceptionDefinition;
import arina.maplab.definitions.mapforce.model.Component;

public class MfdLoaderKind18 implements IMfdComponentDefinitionLoader
{
    @Override
    public IMfdComponentDefinition newInstance(Component c)
    {
        return new MfdExceptionDefinition();
    }
}
