package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.model.Component;

import java.util.*;

abstract public class MfdComponentDefinition implements IMfdComponentDefinition
{
    Map<String, Map.Entry<String, Integer>> linksMap = new HashMap<>();
    List<String> inputList = new ArrayList<>();
    List<String> outputList = new ArrayList<>();
    String id;
    String name;
    String globalPrefix;

    @Override
    public String getId()
    {
        return id;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public boolean isInput()
    {
        return false;
    }

    @Override
    public boolean isOutput()
    {
        return false;
    }

    @Override
    public void load(String globalPrefix, Component component, Map<String, IMapComponentDefinition> connectorsMap) throws Exception
    {
        this.globalPrefix = globalPrefix;
        this.id = globalPrefix + component.getUid();
        this.name = component.getName();
    }

    @Override
    public void link(String output, String input, Integer linkType)
    {
       linksMap.put(input, new AbstractMap.SimpleEntry<>(output, linkType));
    }

    @Override
    public List<String> getInputList()
    {
        return inputList;
    }

    @Override
    public List<String> getOutputList()
    {
        return outputList;
    }

    @Override
    public Map.Entry<String, Integer> getLink(String index)
    {
        return linksMap.get(index);
    }
}
