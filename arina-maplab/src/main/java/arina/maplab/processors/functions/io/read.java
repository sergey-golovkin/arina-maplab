package arina.maplab.processors.functions.io;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.net.URL;

public class read extends MapLibraryFunctionProcessor
{
    public read(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue path = computeInputParameter(0, context);
        IMapValue encoding = computeInputParameter(1, context);

        if (path.isNull())
            return MapValue.NULL;

        String encodingStr;

        if(encoding.isNull() || StringUtils.isEmpty(encoding.getValue(String.class)))
            encodingStr = "utf8";
        else
            encodingStr = encoding.getValue(String.class);

        String spath = path.getValue(String.class);
        if(spath.startsWith("http:") || spath.startsWith("https:") || spath.startsWith("ftp:") || spath.startsWith("ftps:"))
        {
            return new MapValue(this, IOUtils.toString(new URL(spath).openStream(), encodingStr));
        }
        else
        {
            if ( ! spath.startsWith("classpath:") && ! spath.startsWith("file:"))
                spath = "file:" + spath;

            // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/support/PathMatchingResourcePatternResolver.html
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(getClass().getClassLoader());
            Resource[] resources = resolver.getResources(spath);
            for (Resource resource : resources)
                return new MapValue(this, FileUtils.readFileToString(resource.getFile(), encodingStr));
        }
        return MapValue.NULL;
    }
}
