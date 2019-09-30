package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.definitions.MfdModel;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.maplab.value.ValueProcessor;
import arina.utils.TypesUtils;

public class MapOutputProcessor extends MapComponentProcessor
{
    final protected String datatype;
    final protected String usageKind;
    final private DataTypeProcessor p = new DataTypeProcessor();

    class DataTypeProcessor extends ValueProcessor
    {
        @Override
        protected Object processValue(Object value) throws Exception
        {
            return TypesUtils.getValue(datatype, value);
        }
    }

    public MapOutputProcessor(IMapComponentDefinition definition, String datatype, String usageKind)
    {
        super(definition);

        this.datatype = datatype;
        this.usageKind = usageKind;
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        if(definition.getInputList().size() > 0 && ! MfdModel.isEmptyIndex(definition.getInputList().get(0)))
        {
            IMapValue value = computeInputParameter(0, context);
            return p.process(value);
        }
        return getDefault(context);
    }

    protected IMapValue getDefault(IMapContext context) throws Exception
    {
        return MapValue.NULL;
    }
}