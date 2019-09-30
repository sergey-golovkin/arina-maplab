package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_unsignedbyte extends baseConverter
{
    public convert_to_unsignedbyte(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "unsignedByte";
    }
}
