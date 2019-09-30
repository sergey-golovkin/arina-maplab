package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_duration extends baseConverter
{
    public convert_to_duration(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "duration";
    }
}
