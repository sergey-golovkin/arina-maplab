package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.model.Component;
import arina.maplab.definitions.mapforce.model.Valuemap;
import arina.maplab.processors.IMapComponentProcessor;
import arina.maplab.processors.MapValueMapProcessor;
import arina.utils.TypesUtils;
import java.util.HashMap;
import java.util.Map;

public class MfdValueMapDefinition extends MfdSTDefinition
{
    IMapComponentProcessor processor;

    @Override
    public void load(String globalPrefix, Component component, Map<String, IMapComponentDefinition> connectorsMap) throws Exception
    {
        super.load(globalPrefix, component, connectorsMap);

        Map<Object, Object> map = new HashMap<>();
        Object defaultValue = null;
        boolean defaultValueEnabled = false;

        if(component.getData() != null && component.getData().getValuemap() != null && component.getData().getValuemap().getValuemapTable() != null)
        {
            for(Valuemap.ValuemapTable.Entry e : component.getData().getValuemap().getValuemapTable().getEntry())
                map.put(TypesUtils.getValue(component.getData().getValuemap().getInput().getType().value(), e.getFrom()), TypesUtils.getValue(component.getData().getValuemap().getResult().getType().value(), e.getTo()));

            defaultValue = TypesUtils.getValue(component.getData().getValuemap().getResult().getType().value(), component.getData().getValuemap().getResult().getDefaultValue());
            defaultValueEnabled = (component.getData().getValuemap().getEnableDefaultValue() != null && component.getData().getValuemap().getEnableDefaultValue() == 1);
        }

        processor = new MapValueMapProcessor(this, map, component.getData().getValuemap().getInput().getType().value(), defaultValue, defaultValueEnabled);
    }

    @Override
    public IMapComponentProcessor getProcessor() throws Exception
    {
        return processor;
    }
}