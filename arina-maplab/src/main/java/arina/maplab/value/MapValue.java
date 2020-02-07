package arina.maplab.value;

import arina.maplab.processors.IMapComponentProcessor;
import arina.utils.TypesUtils;

import javax.xml.datatype.DatatypeConfigurationException;

public class MapValue implements IMapValue
{
    final public static IMapValue NULL = new MapValue(null, null);
    final public static IMapValue TRUE = new MapValue(null, true);
    final public static IMapValue FALSE = new MapValue(null, false);

    final private IMapComponentProcessor processor;
    final private Object value;
    final private String context;
    final private long scn;

    public MapValue(IMapComponentProcessor processor, Object value)
    {
        this.processor = processor;
        this.value = value;
        this.context = null;
        this.scn = System.currentTimeMillis();
    }

    public MapValue(IMapComponentProcessor processor, Object value, long scn)
    {
        this.processor = processor;
        this.value = value;
        this.context = null;
        this.scn = scn;
    }

    public MapValue(IMapComponentProcessor processor, Object value, String context, long scn)
    {
        this.processor = processor;
        this.value = value;
        this.context = context;
        this.scn = scn;
    }

    @Override
    public IMapValue create(Object newValue)
    {
        return new MapValue(processor, newValue, context, scn);
    }

    @Override
    public IMapComponentProcessor getProcessor()
    {
        return processor;
    }

    @Override
    public <T> T getValue(Class<T> clazz) throws Exception
    {
        if(clazz == null || value == null || clazz.isAssignableFrom(value.getClass()))
            return (T) value;
        else
            return TypesUtils.getValue(clazz, value);
    }

    @Override
    public Object getValue()
    {
        return value;
    }

    @Override
    public String getContext()
    {
        return context;
    }

    @Override
    public boolean isNotNull()
    {
        return ! isNull();
    }

    @Override
    public boolean isNull()
    {
        return value == null || (value instanceof String && ((String) value).length() == 0);
    }

    @Override
    public long getSCN()
    {
        return scn;
    }
}