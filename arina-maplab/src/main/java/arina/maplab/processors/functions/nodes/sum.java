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

public class sum extends MapLibraryFunctionProcessor
{
    public sum(IMapComponentDefinition definition, Integer growable)
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
                BigDecimal result = new BigDecimal(0);
                ArrayList<Object> nodes = new ArrayList<>();
                addValue(nodes, nodesValue.getValue(), true);

                for (Object item : nodes)
                {
                    IMapValue valueValue = computeInputParameter(1, new ValueContext(context, nodesValue.create(item)));
                    if(valueValue.isNotNull())
                    {
                        ArrayList<Object> results = new ArrayList<>();
                        addValue(results, valueValue.getValue(), true);

                        for (Object item2 : results)
                        {
                            if (item2 != null)
                                result = result.add(TypesUtils.getValue(BigDecimal.class, item2));
                        }
                    }
                }
                return new MapValue(this, result);
            }
        }
        return MapValue.NULL;
    }
}
