package arina.maplab.processors.functions.date;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class format extends MapLibraryFunctionProcessor
{
    public format(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        IMapValue format = computeInputParameter(1, context);

        if(value.isNotNull() && format.isNotNull())
        {
            XMLGregorianCalendar xmlgc = value.getValue(XMLGregorianCalendar.class);
            DateFormat df = new SimpleDateFormat(format.getValue(String.class));
            if(df != null && xmlgc != null)
            {
                GregorianCalendar gc = xmlgc.toGregorianCalendar();
                Date date = gc.getTime();
                return new MapValue(this, df.format(date));
            }
        }
        return MapValue.NULL;
    }
}
