package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_boolean extends baseConverter
{
    public convert_to_boolean(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "boolean";
    }
}
