package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_short extends baseConverter
{
    public convert_to_short(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "short";
    }
}
