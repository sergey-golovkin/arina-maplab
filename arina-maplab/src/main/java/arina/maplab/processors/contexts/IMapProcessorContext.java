package arina.maplab.processors.contexts;

import arina.maplab.processors.IMapComponentProcessor;
import java.util.Map;

public interface IMapProcessorContext extends IMapContext
{
    Map<String, IMapComponentProcessor> getProcessors();
}
