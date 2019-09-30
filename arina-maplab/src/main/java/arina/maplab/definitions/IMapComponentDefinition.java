package arina.maplab.definitions;

import arina.maplab.processors.IMapComponentProcessor;
import java.util.List;
import java.util.Map;

public interface IMapComponentDefinition
{
    String getId();
    String getName();
    boolean isInput();
    boolean isOutput();
    List<String> getInputList();
    List<String> getOutputList();
    Map.Entry<String, Integer> getLink(String index); // Map.Entry<outindex, linktype>
    IMapComponentProcessor getProcessor() throws Exception;
    void link(String output, String input, Integer linkType) throws Exception;
}
