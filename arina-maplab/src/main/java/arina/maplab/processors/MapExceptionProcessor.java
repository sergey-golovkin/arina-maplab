package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.util.ArrayList;
import java.util.List;

public class MapExceptionProcessor extends MapComponentProcessor
{
	public class Oops extends Exception
	{
		final List<String> stackTrace;

		public Oops(String msg, List<String> stackTrace)
		{
			super(msg);

			this.stackTrace = stackTrace;
		}

		public List<String> stackTrace()
		{
			return stackTrace;
		}

		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder(this.getMessage());
			sb.append("\n\nStackTrace:\n");
			stackTrace.forEach(v -> sb.append(v).append("\n"));
			return sb.toString();
		}
	}

    public MapExceptionProcessor(IMapComponentDefinition definition)
    {
        super(definition);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
	    IMapValue value = computeInputParameter(0, context);

		if(value.isNotNull())
		{
			List<String > stackTrace = new ArrayList<>();
			fillStackTrace(stackTrace, context, 0);
			throw new Oops(computeInputParameter(1, context).getValue(String.class), stackTrace);
		}
	    return MapValue.NULL;
    }
}