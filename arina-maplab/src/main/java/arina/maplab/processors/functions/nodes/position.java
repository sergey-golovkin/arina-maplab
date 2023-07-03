package arina.maplab.processors.functions.nodes;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.contexts.IValueContext;
import arina.maplab.processors.contexts.ParentValueContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.util.ArrayList;
import java.util.List;

public class position extends MapLibraryFunctionProcessor
{
    public position(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IValueContext valueContext = context.getContext(IValueContext.class);
        if(valueContext != null)
        {
            IMapValue nodesValue = computeInputParameter(0, context.getParent());

            if (nodesValue.isNotNull())
            {
                ArrayList<Object> nodes = new ArrayList<>();
                addValue(nodes, nodesValue.getValue(), true);

                int i = 0;
                for(Object item : nodes)
                {
                    if (valueContext.getValue().getValue() == item)
                    {
                        return new MapValue(this, i);
                    }
                    i++;
                }
            }
        }
        return MapValue.NULL;
    }
//    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
//    {
//        IValueContext valueContext = context.getContext(IValueContext.class);
//        if(valueContext != null)
//        {
//            IMapValue nodesValue = computeInputParameter(0, new ParentValueContext(context, valueContext.getValue()));
//
//            if (nodesValue.isNotNull())
//            {
//                ArrayList<Object> nodes = new ArrayList<>();
//                addValue(nodes, nodesValue.getValue(), true);
//
//                int i = 0;
//                for(Object item : nodes)
//                {
//                    if (valueContext.getValue().getValue() == item)
//                    {
//                        return new MapValue(this, i);
//                    }
//                    i++;
//                }
//            }
//        }
//        return MapValue.NULL;
//    }
}
