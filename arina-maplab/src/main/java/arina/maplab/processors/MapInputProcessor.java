package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IInputValueContext;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.TypesUtils;

public class MapInputProcessor extends MapOutputProcessor
{
    final private IMapValue previewvalue;

    public MapInputProcessor(IMapComponentDefinition definition, String datatype, Object previewvalue, String usageKind)
    {
        super(definition, datatype, usageKind);

        this.previewvalue = new MapValue(this, previewvalue);
    }

    @Override
    protected IMapValue getDefault(IMapContext context) throws Exception
    {
        IInputValueContext ivc = context.getContext(IInputValueContext.class);
        IMapValue result = MapValue.NULL;
        if(ivc != null)
            result = ivc.getValue(definition.getName());

        if(result.isNull())
            return previewvalue;
        else
            return result.create(TypesUtils.getValue(datatype, result.getValue()));
    }
}