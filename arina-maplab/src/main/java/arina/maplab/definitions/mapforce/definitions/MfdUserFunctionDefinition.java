package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.model.Component;
import arina.maplab.definitions.mapforce.model.Entry;
import arina.maplab.definitions.mapforce.model.Root;
import arina.maplab.processors.IMapComponentProcessor;
import arina.maplab.processors.MapUserFunctionProcessor;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class MfdUserFunctionDefinition extends MfdComponentDefinition
{
    final Map<String, String> inputParameters = new HashMap<>();
    final Map<String, Map.Entry<String, String>> outputParameters = new HashMap<>();
    IMapComponentProcessor processor;

    @Override
    public void load(String globalPrefix, Component component, Map<String, IMapComponentDefinition> connectorsMap) throws Exception
    {
        super.load(globalPrefix, component, connectorsMap);

        if(component.getData() != null)
        {
            for(Root r : component.getData().getRoot())
            {
                for (Entry e : r.getEntry())
                {
                    if(e.getInpkey() == null && e.getOutkey() == null)
                    {
                        processEntries(globalPrefix, e.getEntry().get(0), component, connectorsMap, e.getComponentid(), "/");
                    }
                    else
                    {
                        processEntries(globalPrefix, e, component, connectorsMap, e.getComponentid(), e.getName());
                    }
                }
            }
        }

        processor = new MapUserFunctionProcessor(this, inputParameters, outputParameters);
    }

    private void processEntries(String globalPrefix, Entry entry, Component component, Map<String, IMapComponentDefinition> connectorsMap, Integer componentId, String name)
    {
        if (entry.getInpkey() != null)
        {
            String index = globalPrefix + entry.getInpkey();
            inputList.add(index);
            connectorsMap.put(index, this);
            if(componentId != null)
                inputParameters.put(index, name);
        }

        if (entry.getOutkey() != null)
        {
            String index = globalPrefix + entry.getOutkey();
            outputList.add(index);
            connectorsMap.put(index, this);
            if(componentId != null)
                outputParameters.put(index, new AbstractMap.SimpleEntry<>(MfdModel.getPrefix(component.getLibrary(), component.getName()) + name, name));
        }

        for(Entry e : entry.getEntry())
            processEntries(globalPrefix, e, component, connectorsMap, componentId, name + ("/".equals(name) ? "" : "/") + e.getName());
    }

    @Override
    public IMapComponentProcessor getProcessor() throws Exception
    {
        return processor;
    }
}
