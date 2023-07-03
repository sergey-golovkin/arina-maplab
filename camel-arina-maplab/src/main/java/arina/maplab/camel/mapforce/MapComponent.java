package arina.maplab.camel.mapforce;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.support.DefaultComponent;

import java.util.Map;

public class MapComponent extends DefaultComponent
{
    final public static String CACHE = "cache";
    final public static String REFRESH = "refresh";

    public MapComponent()
    {
    }

    public MapComponent(CamelContext context)
    {
        super(context);
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception
    {
        String mode;
        long refreshDelay;
        boolean validate;

        mode = getAndRemoveParameter(parameters, "mode", String.class, MapComponent.CACHE);
        if( ! mode.equals(MapComponent.CACHE) && ! mode.equals(MapComponent.REFRESH))
            throw new Exception("Invalid mode: (" + mode + "). Must be mode=cache or mode=refresh");

        refreshDelay = getAndRemoveParameter(parameters, "refreshDelay", Long.class, 0L);
        validate = getAndRemoveParameter(parameters, "validate", Boolean.class, true);

        return new MapEndpoint(uri, this, remaining, mode, refreshDelay, validate);
    }
}
