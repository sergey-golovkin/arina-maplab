package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_time extends baseConverter
{
    public convert_to_time(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "time";
    }
}
