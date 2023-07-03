package arina.maplab.processors.functions.nodes;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.contexts.ValueContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.TypesUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

public class concat extends MapLibraryFunctionProcessor
{
    public concat(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        if(index != null)
        {
            IMapValue nodesValue = computeInputParameter(0, context);

            if (nodesValue.isNotNull())
            {
                String result = null;
                ArrayList<Object> nodes = new ArrayList<>();
                addValue(nodes, nodesValue.getValue(), true);

                for (Object item : nodes)
                {
                    IMapContext c = new ValueContext(context, nodesValue.create(item));
                    IMapValue stringValue = computeInputParameter(1, c);
                    if(stringValue.isNotNull())
                    {
                        IMapValue delimiter = computeInputParameter(3, c);
                        ArrayList<Object> strings = new ArrayList<>();
                        addValue(strings, stringValue.getValue(), true);

                        for (Object part : strings)
                        {
                            if (part != null)
                                if(result == null)
                                    result = TypesUtils.getValue(String.class, part);
                                else
                                    result += (delimiter.isNotNull() ? delimiter.getValue(String.class) : "") + TypesUtils.getValue(String.class, part);
                        }
                    }
                }

                if(result != null)
                {
                    IMapValue prefix = computeInputParameter(2, context);
                    IMapValue suffix = computeInputParameter(4, context);
                    return new MapValue(this, (prefix.isNotNull() ? prefix.getValue(String.class) : "") + result + (suffix.isNotNull() ? suffix.getValue(String.class) : ""));
                }
            }
        }
        return MapValue.NULL;
    }
}
