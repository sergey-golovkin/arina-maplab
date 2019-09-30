package arina.maplab.processors.contexts;

public class MapContext implements IMapContext
{
    final protected IMapContext parent;

    public MapContext(IMapContext parent)
    {
        this.parent = parent;
    }

    @Override
    public <T> T getContext(Class<T> contextType)
    {
        if (contextType.isAssignableFrom(this.getClass()))
                return (T) this;

        if(parent != null)
            return (T) parent.getContext(contextType);
        else
            return null;
    }

    @Override
    public IMapContext getParent()
    {
        return parent;
    }
}
