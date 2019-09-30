package arina.maplab.processors.functions.logical;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

public class equal extends MapLibraryFunctionProcessor
{
    public equal(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue op1 = computeInputParameter(0, context);
        IMapValue op2 = computeInputParameter(1, context);

        if(op1.isNull() && op2.isNull())
            return MapValue.TRUE;

        if(op1.isNull() && op2.isNotNull() || op1.isNotNull() && op2.isNull())
            return MapValue.FALSE;

        if(op1.getValue(String.class).equals(op2.getValue(String.class)))
            return MapValue.TRUE;
        else
            return MapValue.FALSE;
    }
}
