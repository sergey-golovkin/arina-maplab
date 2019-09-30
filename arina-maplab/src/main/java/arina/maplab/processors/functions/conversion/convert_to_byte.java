package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_byte extends baseConverter
{
    public convert_to_byte(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "byte";
    }
}
