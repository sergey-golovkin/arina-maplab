package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

public class MapConstantProcessor extends MapComponentProcessor
{
    final private IMapValue value;

    public MapConstantProcessor(IMapComponentDefinition definition, Object value)
    {
        super(definition);

        this.value = new MapValue(this, value);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        return value;
    }
}
