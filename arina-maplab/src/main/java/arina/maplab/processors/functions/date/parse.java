package arina.maplab.processors.functions.date;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import javax.xml.datatype.DatatypeFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class parse extends MapLibraryFunctionProcessor
{
    public parse(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        IMapValue format = computeInputParameter(1, context);
        IMapValue timezone = computeInputParameter(2, context);
        IMapValue lenient = computeInputParameter(3, context);

        if(value.isNotNull() && format.isNotNull())
        {
            DateFormat df = new SimpleDateFormat(format.getValue(String.class));
            if(timezone.isNotNull())
                df.setTimeZone(TimeZone.getTimeZone(timezone.getValue(String.class)));

            if(lenient.isNotNull())
                df.setLenient(lenient.getValue(Boolean.class));

            if(df != null)
            {
                Date d = df.parse(value.getValue(String.class));
                if(d != null)
                {
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTime(d);
                    return new MapValue(this, DatatypeFactory.newInstance().newXMLGregorianCalendar(gc));
                }
            }
        }
        return MapValue.NULL;
    }
}
