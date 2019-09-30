package arina.maplab.definitions.mapforce.loaders;

import arina.maplab.definitions.mapforce.definitions.IMfdComponentDefinition;
import arina.maplab.definitions.mapforce.definitions.MfdUserFunctionDefinition;
import arina.maplab.definitions.mapforce.model.Component;

public class MfdLoaderKind19 implements IMfdComponentDefinitionLoader
{
    @Override
    public IMfdComponentDefinition newInstance(Component c)
    {
        return new MfdUserFunctionDefinition();
    }
}
