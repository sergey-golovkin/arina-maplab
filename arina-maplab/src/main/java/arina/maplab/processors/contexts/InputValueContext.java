package arina.maplab.processors.contexts;

import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.util.HashMap;
import java.util.Map;

public class InputValueContext extends MapContext implements IInputValueContext
{
    final Map<String, IMapValue> values = new HashMap<>();

    public InputValueContext(IMapContext parent, String name, IMapValue value)
    {
        super(parent);
        values.put(name, value);
    }

    @Override
    public IMapValue getValue(String name)
    {
        IMapValue value = values.get(name);
        if (value != null)
            return value;

        for (IMapContext c = getParent(); c != null; c = c.getParent())
        {
            if (c instanceof IInputValueContext)
                return ((IInputValueContext) c).getValue(name);
        }

        return MapValue.NULL;
    }
}

