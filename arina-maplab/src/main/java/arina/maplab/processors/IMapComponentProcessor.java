package arina.maplab.processors;

import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.value.IMapValue;

import java.util.List;
import java.util.Map;

public interface IMapComponentProcessor
{
    IMapValue getValue(String index, IMapContext context) throws Exception;
    Map<String, IMapValue> getOutputs(IMapContext context) throws Exception;
    void fillStackTrace(List<String> stackTrace, IMapContext context, int level) throws Exception;
}
