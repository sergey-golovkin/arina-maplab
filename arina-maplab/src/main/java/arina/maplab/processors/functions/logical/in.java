package arina.maplab.processors.functions.logical;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.TypesUtils;

public class in extends MapLibraryFunctionProcessor
{
    public in(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        Object op1 = null;
        for(String item : this.definition.getInputList())
        {
            IMapValue result = computeInputValue(item, context);
            if(op1 == null)
            {
                op1 = TypesUtils.toString(result.getValue());
                if(op1 == null)
                    break;
            }
            else
            {
                if (result.isNotNull())
                {
                    if(op1.equals(TypesUtils.toString(result.getValue())))
                        return MapValue.TRUE;
                }
            }
        }
        return MapValue.FALSE;
    }
}
