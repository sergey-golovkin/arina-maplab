package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_unsignedint extends baseConverter
{
    public convert_to_unsignedint(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "unsignedInt";
    }
}
