package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_decimal extends baseConverter
{
    public convert_to_decimal(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "decimal";
    }
}
