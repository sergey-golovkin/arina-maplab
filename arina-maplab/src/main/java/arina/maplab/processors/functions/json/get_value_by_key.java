package arina.maplab.processors.functions.json;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class get_value_by_key extends MapLibraryFunctionProcessor {

    public get_value_by_key(IMapComponentDefinition definition, Integer growable) {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue object = computeInputParameter(0, context);
        IMapValue tagName = computeInputParameter(1, context);

        if (object.isNotNull() && tagName.isNotNull())
        {
            Object value = object.getValue();
            if (value instanceof ArrayList)
            {
                ArrayList arrayList = (ArrayList) value;
                if (!arrayList.isEmpty())
                {
                    for (Object o : arrayList)
                    {
                        if (o instanceof Map)
                        {
                            HashMap hashMap = (HashMap) o;
                            Object foundValue = hashMap.get(tagName.getValue(String.class));
                            if (foundValue instanceof String)
                            {
                                return new MapValue(this, foundValue);
                            }
                        }
                    }
                }
            }
            JSONObject jsonObject = new JSONObject(object.getValue(String.class));
            String name = tagName.getValue(String.class);
            if (jsonObject.has(name))
            {
                return new MapValue(this, jsonObject.get(name));
            }
        }

        return MapValue.NULL;
    }
}