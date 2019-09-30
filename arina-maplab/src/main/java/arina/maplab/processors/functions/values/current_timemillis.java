package arina.maplab.processors.functions.values;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import javax.xml.datatype.DatatypeFactory;
import java.util.GregorianCalendar;

public class current_timemillis extends MapLibraryFunctionProcessor
{
    public current_timemillis(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        return new MapValue(this, System.currentTimeMillis());
    }
}
