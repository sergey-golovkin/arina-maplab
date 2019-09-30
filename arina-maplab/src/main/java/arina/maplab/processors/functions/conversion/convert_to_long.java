package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_long extends baseConverter
{
    public convert_to_long(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "long";
    }
}
