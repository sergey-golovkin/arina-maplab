package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.OopsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapExceptionProcessor extends MapComponentProcessor
{
    public MapExceptionProcessor(IMapComponentDefinition definition)
    {
        super(definition);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
	    IMapValue value = computeInputParameter(0, context);

		if(value.isNotNull())
		{
            List<String > stackTrace = new ArrayList<>();
            Map<String, String> dump = new HashMap<>();
            fillStackTrace(stackTrace, dump, context, 0);
			throw new OopsException(computeInputParameter(1, context).getValue(String.class), stackTrace, dump, null);
		}
	    return MapValue.NULL;
    }
}