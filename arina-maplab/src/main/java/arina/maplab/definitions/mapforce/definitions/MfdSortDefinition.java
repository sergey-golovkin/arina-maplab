package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.model.Component;
import arina.maplab.definitions.mapforce.model.Sort;
import arina.maplab.processors.IMapComponentProcessor;
import arina.maplab.processors.MapSortProcessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MfdSortDefinition extends MfdSTDefinition
{
    List<Integer> directions = new ArrayList<>();
    String language;
    String country;
    IMapComponentProcessor processor;


    @Override
    public void load(String globalPrefix, Component component, Map<String, IMapComponentDefinition> connectorsMap) throws Exception
    {
        super.load(globalPrefix, component, connectorsMap);

        if(component.getData().getSort() != null)
        {
            if(component.getData().getSort().getCollation() != null)
            {
                language = component.getData().getSort().getCollation().getLanguage();
                country = component.getData().getSort().getCollation().getCountry();
            }

            for(Sort.Key key : component.getData().getSort().getKey())
            {
                directions.add("ascending".equals(key.getDirection()) ? 1 : -1);
            }
        }

        processor = new MapSortProcessor(this, language, country, directions);
    }

    @Override
    public IMapComponentProcessor getProcessor() throws Exception
    {
        return processor;
    }

}