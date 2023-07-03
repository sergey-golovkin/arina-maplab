package arina.maplab;

import arina.maplab.definitions.MapModel;
import arina.maplab.definitions.mapforce.definitions.MfdModel;
import arina.maplab.definitions.mapforce.model.Mapping;
import arina.maplab.processors.MapProcessor;
import arina.maplab.value.IMapValue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
//        MapProcessor processor = new MapProcessor(model);
//        for(Map.Entry<String, IMapValue> item : processor.getOutputs().entrySet())
//            System.out.println("key = " + item.getKey() + "\tvalue = " + item.getValue().getValue(String.class));

        byte[] encoded = Files.readAllBytes(Paths.get("D:\\!Work\\altova\\test.json"));
//byte[] encoded = Files.readAllBytes(Paths.get("C:\\Users\\IVandryukhin\\Desktop\\bordero-collective_credit.json"));
        String data = new String(encoded, StandardCharsets.UTF_8);
//String data = "Первый test";
        MapProcessor processor = new MapProcessor(model).set("body", data);
        for(Map.Entry<String, IMapValue> item : processor.getOutputs().entrySet())
            System.out.println("key = " + item.getKey() + "\tvalue = " + item.getValue().getValue(String.class));
    }

//    static String mfd_file = "D:\\Tmp\\arina\\bordero-constructor.mfd";
    static String mfd_file = "D:\\!Work\\altova\\bordero-constructor.mfd";
}
