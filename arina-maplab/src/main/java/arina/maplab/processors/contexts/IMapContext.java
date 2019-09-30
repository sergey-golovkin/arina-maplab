package arina.maplab.processors.contexts;

public interface IMapContext
{
    <T> T getContext(Class<T> contextType);
    IMapContext getParent();
}
