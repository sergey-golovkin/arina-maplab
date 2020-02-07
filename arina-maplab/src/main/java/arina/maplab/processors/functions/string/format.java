package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import javax.xml.datatype.XMLGregorianCalendar;

public class format extends MapLibraryFunctionProcessor
{
    public format(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue format = computeInputParameter(0, context);
        Object[] params = new Object[this.definition.getInputList().size() - 1];

        for(int i = 1; i < this.definition.getInputList().size(); i++)
        {
            Object value = computeInputParameter(i, context).getValue();
            params[i - 1] = (value instanceof XMLGregorianCalendar ? ((XMLGregorianCalendar)value).toGregorianCalendar().getTime() : value);
        }
        return new MapValue(this, String.format(format.getValue(String.class), params));
    }
}
