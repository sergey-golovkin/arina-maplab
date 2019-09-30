package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.definitions.MfdVariableDefinition;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.Marshall;
import arina.utils.Reflection;
import arina.utils.Unmarshall;
import java.util.Map;

public class MapXMLProcessor extends MapVariableProcessor
{
    public MapXMLProcessor(IMapComponentDefinition definition, Map<String, MfdVariableDefinition.ConnectorDef> connectors, Map<String, MfdVariableDefinition.FieldDef> fields, String usageKind, String componentInput, String componentOutput)
    {
        super(definition, connectors, fields, usageKind, componentInput, componentOutput);
    }

    @Override
    protected IMapValue marshal(Object value) throws Exception
    {
        return new MapValue(this, Marshall.xml(Reflection.map2Object((Map<String, Object>) value, fields.get("/").clazz)), scn);
    }

    @Override
    protected Object unmarshal(Object value) throws Exception
    {
        return Unmarshall.xml(value.toString(), null);
    }
}

