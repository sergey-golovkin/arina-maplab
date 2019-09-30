package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_base64binary extends baseConverter
{
    public convert_to_base64binary(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "base64Binary";
    }
}
