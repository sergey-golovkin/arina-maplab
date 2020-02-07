package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.definitions.MfdModel;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.maplab.value.ValueProcessor;
import arina.utils.TypesUtils;

import java.util.Map;

public class MapValueMapProcessor extends MapComponentProcessor
{
    final private Map<Object, Object> map;
    final String datatype;
    final private Object defaultValue;
    final private boolean defaultValueEnabled;
    final private MapValueProcessor p = new MapValueProcessor();

    class MapValueProcessor extends ValueProcessor
    {
        @Override
        protected Object processValue(Object value) throws Exception
        {
            Object key = TypesUtils.getValue(datatype, value);

            if (key != null && map.containsKey(key))
                return map.get(key);
            else
            {
                if (defaultValueEnabled)
                    return defaultValue;
                else
                    throw new Exception("No map value for <" + key + ">");
            }
        }
    }

    public MapValueMapProcessor(IMapComponentDefinition definition, Map<Object, Object> map, String datatype, Object defaultValue, boolean defaultValueEnabled)
    {
        super(definition);

        this.map = map;
        this.datatype = datatype;
        this.defaultValue = defaultValue;
        this.defaultValueEnabled = defaultValueEnabled;
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        if(definition.getInputList().size() > 0 && ! MfdModel.isEmptyIndex(definition.getInputList().get(0)))
        {
            IMapValue value = computeInputParameter(0, context);
            return p.process(value);
        }
        return MapValue.NULL;
    }

    @Override
    public String getName(String index) throws Exception
    {
        return definition.getName();
    }
}