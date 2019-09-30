package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_date extends baseConverter
{
    public convert_to_date(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "date";
    }
}
