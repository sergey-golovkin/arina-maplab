package arina.maplab.processors.contexts;

import arina.maplab.value.IMapValue;

public interface IInputValueContext extends IMapContext
{
    IMapValue getValue(String name);
}
