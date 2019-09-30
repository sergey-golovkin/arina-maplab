package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_int extends baseConverter
{
    public convert_to_int(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "int";
    }
}
