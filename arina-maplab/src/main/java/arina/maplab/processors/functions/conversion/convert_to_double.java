package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_double extends baseConverter
{
    public convert_to_double(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "double";
    }
}
