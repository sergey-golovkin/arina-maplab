package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.definitions.MfdVariableDefinition;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.Marshall;
import arina.utils.Unmarshall;
import java.util.Map;

public class MapJSONProcessor extends MapVariableProcessor
{
    public MapJSONProcessor(IMapComponentDefinition definition, Map<String, MfdVariableDefinition.ConnectorDef> connectors, Map<String, MfdVariableDefinition.FieldDef> fields, String usageKind, String componentInput, String componentOutput)
    {
        super(definition, connectors, fields, usageKind, componentInput, componentOutput);
    }

    @Override
    protected IMapValue marshal(Object value) throws Exception
    {
        return new MapValue(this, Marshall.json(value, null), scn);
    }

    @Override
    protected Object unmarshal(Object value) throws Exception
    {
        return Unmarshall.json(value.toString(), null, null);
    }
}

