package arina.maplab.definitions;

import arina.maplab.definitions.mapforce.model.Mapping;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class MapModel implements IMapModel
{
    private static Unmarshaller getUnmarshaller(boolean validate) throws JAXBException, SAXException
    {
        JAXBContext context = JAXBContext.newInstance(Mapping.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        if(validate)
        {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new StreamSource(MapModel.class.getClassLoader().getResourceAsStream("MapForce.xsd")));
            unmarshaller.setSchema(schema);
        }
        return unmarshaller;
    }

    public static List<Mapping> load(List<String> paths, boolean validate) throws JAXBException, SAXException, IOException
    {
        Unmarshaller unmarshaller = getUnmarshaller(validate);
        List<Mapping> mappings = new ArrayList<>();
        for(String path : paths)
        {
            // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/support/PathMatchingResourcePatternResolver.html
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(MapModel.class.getClassLoader());
            Resource[] resources = resolver.getResources(path);
            for(Resource resource : resources)
                mappings.add((Mapping) unmarshaller.unmarshal(resource.getInputStream()));
        }
        return mappings;
    }
}
