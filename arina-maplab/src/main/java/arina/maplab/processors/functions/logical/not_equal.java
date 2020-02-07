package arina.maplab.processors.functions.logical;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.TypesUtils;

public class not_equal extends MapLibraryFunctionProcessor
{
    public not_equal(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue op1 = computeInputParameter(0, context);
        IMapValue op2 = computeInputParameter(1, context);

        if(op1.isNull() && op2.isNull())
            return MapValue.FALSE;

        if(op1.isNull() && op2.isNotNull() || op1.isNotNull() && op2.isNull())
            return MapValue.TRUE;

        if(TypesUtils.toString(op1.getValue()).equals(TypesUtils.toString(op2.getValue())))
            return MapValue.FALSE;
        else
            return MapValue.TRUE;
    }
}
