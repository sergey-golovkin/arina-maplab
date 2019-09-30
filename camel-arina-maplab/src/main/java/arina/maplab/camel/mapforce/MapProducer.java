package arina.maplab.camel.mapforce;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.definitions.MfdModel;
import arina.maplab.definitions.mapforce.model.Mapping;
import arina.maplab.processors.MapProcessor;
import arina.maplab.processors.contexts.IInputValueContext;
import arina.maplab.processors.contexts.InputValueContext;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.LanguageUtils;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapProducer extends DefaultProducer
{
    final String mfdFile;
    final String mode;
    final long refreshDelay;
    JAXBContext context;
    Unmarshaller unmarshaller;
    Map<String, MfdModel> defs = new ConcurrentHashMap<>();
    long loadedAt = 0;

    MapProducer(MapEndpoint endpoint, String mfdFile, String mode, long refreshDelay, boolean validate) throws JAXBException, SAXException
    {
        super(endpoint);

        this.mfdFile = mfdFile;
        this.mode = mode;
        this.refreshDelay = refreshDelay;

        context = JAXBContext.newInstance(Mapping.class);
        unmarshaller = context.createUnmarshaller();
        if(validate)
        {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new StreamSource(this.getClass().getClassLoader().getResourceAsStream("MapForce.xsd")));
            unmarshaller.setSchema(schema);
        }
    }

    @Override
    public void process(Exchange exchange) throws Exception
    {
        MfdModel def;

        synchronized (defs)
        {
            if (mode.equals(MapComponent.REFRESH) && (System.currentTimeMillis() - loadedAt > refreshDelay))
                defs.clear();

            String mfd = LanguageUtils.evaluate(exchange, mfdFile, String.class);
            def = defs.get(mfd);
            if (def == null)
            {
                List<Mapping> mappings = new ArrayList<>();
                for (String spath : mfd.split(";|,"))
                {
                    if(spath.startsWith("http:") || spath.startsWith("https:") || spath.startsWith("ftp:") || spath.startsWith("ftps:"))
                    {
                        mappings.add((Mapping) unmarshaller.unmarshal(new URL(spath).openStream()));
                    }
                    else
                    {
                        if( ! spath.startsWith("classpath:") && ! spath.startsWith("file:"))
                            spath = "file:" + spath;

                        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(getClass().getClassLoader());
                        Resource[] resources = resolver.getResources(spath);
                        for (Resource resource : resources)
                            mappings.add((Mapping) unmarshaller.unmarshal(resource.getInputStream()));
                    }
                }
                def = new MfdModel(mappings);
                defs.put(mfd, def);
                loadedAt = System.currentTimeMillis();
            }
        }

        IInputValueContext context = null;
        for(IMapComponentDefinition mcd : def.getInputsList()) // обработка входящих параметров
            context = new InputValueContext(context, mcd.getName(), new MapValue(null, LanguageUtils.evaluate(exchange, getSimpleName(mcd.getName()), String.class)));

        MapProcessor processor = new MapProcessor(context, def);
        for(Map.Entry<String, IMapValue> item : processor.getOutputs().entrySet()) // обработка результатов
        {
            if (item.getKey().equals("body"))
                exchange.getIn().setBody(item.getValue().getValue(), Object.class);
            else if(item.getKey().matches("^header_(.*)"))
                exchange.getIn().setHeader(item.getKey().replaceAll("^header_(.*)", "$1"), item.getValue().getValue());
            else if(item.getKey().matches("^exchange[P|p]roperty_(.*)"))
                exchange.setProperty(item.getKey().replaceAll("^exchange[P|p]roperty_(.*)", "$1"), item.getValue().getValue());
        }
    }

    private String getSimpleName(String name)
    {
        return "${" +
                name.replaceFirst("^header_(.*)", "header.$1")
                        .replaceFirst("^(exchange)*[P|p]roperty_(.*)", "exchangeProperty.$2")
                        .replaceFirst("^sysenv_(.*)", "sysenv.$1")
                        .replaceFirst("^sys_(.*)", "sys.$1")
                        .replaceFirst("^exception_(.*)", "exception.$1")
                        .replaceFirst("^body_(.*)", "body.$1").replace("_", ".")
                + "}";
    }
}
