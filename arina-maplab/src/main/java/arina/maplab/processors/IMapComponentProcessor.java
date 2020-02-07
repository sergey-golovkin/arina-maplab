package arina.maplab.processors;

import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.value.IMapValue;

import java.util.List;
import java.util.Map;

public interface IMapComponentProcessor
{
    String getId();
    String getName();
    IMapValue getValue(String index, IMapContext context) throws Exception;
    Map<String, IMapValue> getOutputs(IMapContext context) throws Exception;
    void fillStackTrace(List<String> stackTrace, Map<String, String> dump, IMapContext context, int level) throws Exception;
    String getName(String index) throws Exception;
}
