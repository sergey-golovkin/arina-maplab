package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_integer extends baseConverter
{
    public convert_to_integer(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "integer";
    }
}
