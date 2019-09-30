package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;

public class convert_to_qname extends baseConverter
{
    public convert_to_qname(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);

        datatype = "QName";
    }
}
