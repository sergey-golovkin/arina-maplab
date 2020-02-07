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
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        return value;
    }

    @Override
    public String getName(String index) throws Exception
    {
        return definition.getName();
    }
}
