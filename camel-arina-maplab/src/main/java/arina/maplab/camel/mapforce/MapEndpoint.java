package arina.maplab.camel.mapforce;

import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.support.DefaultEndpoint;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;

public class MapEndpoint extends DefaultEndpoint
{
    final String mfdFile;
    final String mode;
    final long refreshDelay;
    final boolean validate;

    MapEndpoint(String uri, Component component, String mfdFile, String mode, long refreshDelay, boolean validate)
    {
        super(uri, component);

        this.mfdFile = mfdFile;
        this.mode = mode;
        this.refreshDelay = refreshDelay;
        this.validate = validate;
    }

    public Consumer createConsumer(Processor processor)
    {
        return null;
    }

    public Producer createProducer() throws JAXBException, SAXException
    {
        return new MapProducer(this, mfdFile, mode, refreshDelay, validate);
    }

    @Override
    public boolean isSingleton()
    {
        return true;
    }

}
