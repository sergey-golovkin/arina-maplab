package arina.maplab.processors;

import arina.maplab.definitions.*;
import arina.maplab.processors.contexts.*;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.util.*;

public class MapProcessor extends MapContext implements IMapProcessorContext
{
    Map<String, IMapComponentProcessor> processorsMap = new HashMap<>();
    List<IMapComponentProcessor> outputsList = new ArrayList<>();
    IMapContext context = this;

    public MapProcessor(IMapModel def) throws Exception
    {
        this(null, def);
    }

    public MapProcessor(IMapContext context, IMapModel def) throws Exception
    {
        super(context);

        for(IMapComponentDefinition d : def.getDefinitionsList())
        {
            IMapComponentProcessor p = d.getProcessor();
            d.getInputList().forEach(v -> processorsMap.put(v, p) );
            d.getOutputList().forEach(v -> processorsMap.put(v, p) );
            def.getOutputsList().forEach(v -> { if(v == d) outputsList.add(p); });
        }
    }

    public Map<String, IMapValue> getOutputs() throws Exception
    {
        Map<String, IMapValue> resultValues = new HashMap<>();
        for(IMapComponentProcessor cp : outputsList)
            resultValues.putAll(cp.getOutputs(context));

        return resultValues;
    }

    public MapProcessor set(String name, Object value)
    {
        context = new InputValueContext(context, name, new MapValue(null, value));
        return this;
    }

    @Override
    public Map<String, IMapComponentProcessor> getProcessors()
    {
        return processorsMap;
    }
}
