package arina.maplab.processors.functions.conversion;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.utils.TypesUtils;

public class baseConverter extends MapLibraryFunctionProcessor
{
    protected String datatype;

    public baseConverter(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        return value.create(TypesUtils.getValue(datatype, value.getValue()));
    }
}
