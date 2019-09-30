package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_float extends baseConverter
{
    public convert_to_float(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "float";
    }
}
