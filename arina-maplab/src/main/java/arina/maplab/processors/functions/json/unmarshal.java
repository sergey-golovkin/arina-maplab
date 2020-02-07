package arina.maplab.processors.functions.json;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.FieldDef;
import arina.utils.Reflection;
import arina.utils.Unmarshall;

import java.util.Map;

public class unmarshal extends MapLibraryFunctionProcessor
{
    public unmarshal(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue jsonString = computeInputParameter(0, context);
        IMapValue dateFormat = computeInputParameter(1, context);
        IMapValue objectType = computeInputParameter(2, context);

        Object obj = Unmarshall.json(jsonString.isNotNull() ? jsonString.getValue(String.class) : null, null, dateFormat.isNotNull() ? dateFormat.getValue(String.class) : null);

        if(objectType.isNotNull())
        {
            Map<String, FieldDef> fields = Reflection.getFieldDefs((Map<String, Object>) obj, objectType.getValue(String.class));
            Reflection.resetTypes(obj, "/", fields);
        }
        return new MapValue(this, obj);
    }
}
