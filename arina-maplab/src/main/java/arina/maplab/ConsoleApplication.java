package arina.maplab;

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
        arrayList.add("file:" + mfd_file);
        MfdModel model = new MfdModel(MapModel.load(arrayList, false));
        MapProcessor processor = new MapProcessor(model);
        for(Map.Entry<String, IMapValue> item : processor.getOutputs().entrySet())
            System.out.println("key = " + item.getKey() + "\tvalue = " + item.getValue().getValue(String.class));
    }

    static String mfd_file = "C:\\work\\arina.idea\\test.mfd";
}
