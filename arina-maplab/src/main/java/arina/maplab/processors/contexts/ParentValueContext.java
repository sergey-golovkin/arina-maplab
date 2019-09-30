package arina.maplab.processors.contexts;

import arina.maplab.value.IMapValue;

public class ParentValueContext extends ValueContext implements IParentValueContext
{
    public ParentValueContext(IMapContext parent, IMapValue value)
    {
        super(parent, value);
    }
}
