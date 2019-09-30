package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_string extends baseConverter
{
    public convert_to_string(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "string";
    }
}
