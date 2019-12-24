# Arina-MapLab is a library that implements the mapping engine.

### Supported mapping formats

- MFD - Altova MapForce xml file format.

# Usages
To use the library from Java, you must initialize the model or list of models, and then pass the model to the processor for execution.

An example of a program transferring input parameters to the model with the names “stringInput”, “intInput” and “dateTimeInput” and receiving a collection of output parameters and displaying their values to the console.

# Example
```java
import arina.maplab.definitions.MapModel;
import arina.maplab.definitions.mapforce.definitions.MfdModel;
import arina.maplab.definitions.mapforce.model.Mapping;
import arina.maplab.processors.MapProcessor;
import arina.maplab.value.IMapValue;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ConsoleApplication
{
    public static void main(String[] args) throws Exception
    {
        JAXBContext context = JAXBContext.newInstance(Mapping.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ArrayList<String> arrayList = new ArrayList<>();
 
        // example of loading a model from a resource in a jar file
        // the following formats are supported:
        // 1. file: - read from files (mask is supported)
        // 2. classpath: - read from resources in jar files (mask is supported)
        // 3. http: or https: - read by the URL of the resource using the http or https protocol
        // 4. ftp: or ftps: - read by resource URL via ftp or ftps

        arrayList.add("classpath:examples/datetime.mfd");
        MfdModel model = new MfdModel(MapModel.load(arrayList, true));
 
        // General note: after loading, the model is read-only, so it can be used in a multi-threaded environment by creating many processors
 
        // passing the model to the processor and passing input parameters into it     
        MapProcessor processor = new MapProcessor(model).set("string", "sring example").set("int", 555).set("dateTime", new Date());
 
        // getting results and displaying them in the console
        for(Map.Entry<String, IMapValue> item : processor.getOutputs().entrySet())
            System.out.println("key = " + item.getKey() + "\tvalue = " + item.getValue().getValue(String.class));
    }
}
```
