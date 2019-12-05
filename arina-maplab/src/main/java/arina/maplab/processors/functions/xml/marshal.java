package arina.maplab.processors.functions.xml;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.Marshall;
import arina.utils.Reflection;

import java.util.Map;

public class marshal extends MapLibraryFunctionProcessor
{
    public marshal(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue object = computeInputParameter(0, context);
        IMapValue objectType = computeInputParameter(1, context);

        return new MapValue(this, Marshall.xml(Reflection.map2Object(object.getValue(Map.class), Class.forName(objectType.getValue(String.class)))));
    }
}
