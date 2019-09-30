package arina.maplab.processors.contexts;

import arina.maplab.value.IMapValue;

public class ValueContext extends MapContext implements IValueContext
{
    final IMapValue value;

    public ValueContext(IMapValue value)
    {
        this(null, value);
    }

    public ValueContext(IMapContext parent, IMapValue value)
    {
        super(parent);
        this.value = value;
    }

    @Override
    public IMapValue getValue()
    {
        return value;
    }
}
