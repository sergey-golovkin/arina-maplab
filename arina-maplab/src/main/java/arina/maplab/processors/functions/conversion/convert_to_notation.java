package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_notation extends baseConverter
{
    public convert_to_notation(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "NOTATION";
    }
}
