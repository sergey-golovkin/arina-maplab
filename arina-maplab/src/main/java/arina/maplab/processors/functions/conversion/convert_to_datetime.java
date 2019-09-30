package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_datetime extends baseConverter
{
    public convert_to_datetime(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "dateTime";
    }
}
