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
        //arrayList.add("classpath:examples/arrayxml_read_and.mfd");
        arrayList.add("file:D:\\My\\work\\7.0_JRE8_CAM2.22\\_XXX_\\examples\\ifelse.mfd");
        MfdModel model = new MfdModel(MapModel.load(arrayList, false));
        MapProcessor processor = new MapProcessor(model);
        for(Map.Entry<String, IMapValue> item : processor.getOutputs().entrySet())
            System.out.println("key = " + item.getKey() + "\tvalue = " + item.getValue().getValue(String.class));


//        MapProcessor processor = new MapProcessor(model).set("string", "Это строка").set("int", 555).set("dateTime", new Date());

//        int i = 0;
//        long s = System.currentTimeMillis();
//        for( ; i < 1000000; i++)
//        {
//            MapProcessor processor = new MapProcessor(model).set("body", body);
//            processor.getOutputs();
//        }
//        long e = System.currentTimeMillis();
//        System.out.println("time = " + (e - s) + " count =" + i);

//        for(Map.Entry<String, IMapValue> item : processor.getOutputs().entrySet())
//            System.out.println("key = " + item.getKey() + "\tvalue = " + item.getValue().getValue(String.class));
    }

    static String body = "{\n" +
            "  \"PCPolicy\":{\n" +
            "    \"Account\":\"10000000461\",\n" +
            "    \"ActionTerritory\":\"1\",\n" +
            "    \"AgentInfoList\":[ {\n" +
            "      \"AgentInfo\":{\n" +
            "        \"AgentExternalCode\":\"1887062\",\n" +
            "        \"AgentRole\":\"4\"\n" +
            "      }\n" +
            "    } ],\n" +
            "    \"AgentStatement\":{\n" +
            "      \"StatementDate\":\"2019-07-25\",\n" +
            "      \"StatementNumber\":\"000000515\"\n" +
            "    },\n" +
            "    \"Branch\":1,\n" +
            "    \"Change\":{\n" +
            "      \"CreateDate\":\"2019-07-25T15:16:22.255Z\",\n" +
            "      \"EffectiveDate\":\"2019-07-25T21:00:00.000Z\",\n" +
            "      \"EndDate\":\"2020-07-25T21:01:00.000Z\",\n" +
            "      \"PeriodPublicID\":\"pcrdev:2607\",\n" +
            "      \"PolicyChangeType\":\"1\",\n" +
            "      \"TransactionNumber\":\"000000866\",\n" +
            "      \"TransactionStatus\":\"1\"\n" +
            "    },\n" +
            "    \"CheckSumCorrect\":\"1\",\n" +
            "    \"ClientSystem\":\"GWPC\",\n" +
            "    \"ContractOption\":\"1\",\n" +
            "    \"ContractSign\":true,\n" +
            "    \"Currency\":\"RUB\",\n" +
            "    \"DetentionTerritory\":62,\n" +
            "    \"DriverPermit\":\"0\",\n" +
            "    \"EndDate\":\"2020-07-25T21:01:00.000Z\",\n" +
            "    \"Insured\":{\n" +
            "      \"Address\":{\n" +
            "        \"AddressType\":\"Registration\",\n" +
            "        \"Area\":\"Шарлыкский р-н\",\n" +
            "        \"Country\":\"643\",\n" +
            "        \"Locality\":\"Шарлык с.\",\n" +
            "        \"PostalCode\":\"461000\",\n" +
            "        \"Region\":\"Оренбургская обл.\",\n" +
            "        \"Street\":\"Ломоносова\",\n" +
            "        \"StreetType\":\"Вал\"\n" +
            "      },\n" +
            "      \"ContactDocumentList\":[ {\n" +
            "        \"DocNumber\":\"326237\",\n" +
            "        \"DocStartDate\":\"2018-09-20\",\n" +
            "        \"DocumentType\":\"12\",\n" +
            "        \"Issued\":\"edl\",\n" +
            "        \"PublicID\":\"pcrdev:2419\",\n" +
            "        \"Serie\":\"4518\",\n" +
            "        \"SubdivisionCode\":\"287-986\"\n" +
            "      } ],\n" +
            "      \"ContactMail\":{\n" +
            "        \"Address\":\"asd1@gmail.com\",\n" +
            "        \"MailType\":\"Private\"\n" +
            "      },\n" +
            "      \"ContactPhone\":{\n" +
            "        \"PhoneNumber\":\"9151234567\",\n" +
            "        \"PhoneType\":\"1\"\n" +
            "      },\n" +
            "      \"ContactSubtype\":\"1\",\n" +
            "      \"Country\":\"643\",\n" +
            "      \"DateOfBirth\":\"1976-05-12\",\n" +
            "      \"FirstName\":\"КАР\",\n" +
            "      \"FullName\":\"КАРЫЧ КАР КАРЫЧ\",\n" +
            "      \"Gender\":\"Male\",\n" +
            "      \"LastName\":\"КАРЫЧ\",\n" +
            "      \"MiddleName\":\"КАРЫЧ\",\n" +
            "      \"PublicID\":\"pcrdev:54406\",\n" +
            "      \"Resident\":\"1\"\n" +
            "    },\n" +
            "    \"InsuredPremium\":\"225858.00\",\n" +
            "    \"InsuredPremiumDate\":\"2019-07-26\",\n" +
            "    \"IssueDate\":\"2019-07-24T21:00:00.000Z\",\n" +
            "    \"Issued\":true,\n" +
            "    \"LiabilityEndDate\":\"2020-07-25T21:01:00.000Z\",\n" +
            "    \"LiabilityStartDate\":\"2019-07-25T21:00:00.000Z\",\n" +
            "    \"Mvehicle\":{\n" +
            "      \"AntiTheftSystem\":\"SystemInstalled\",\n" +
            "      \"CarryingCapacity\":0.0,\n" +
            "      \"CostNewCur\":\"RUR\",\n" +
            "      \"CostNew_amt\":\"1208200.00\",\n" +
            "      \"CoverageList\":[ {\n" +
            "        \"CoverageTypeID\":\"MDamageAtFault\",\n" +
            "        \"Currency\":\"RUB\",\n" +
            "        \"GVSCode\":\"102\",\n" +
            "        \"InsuredPremium\":\"55371.00\",\n" +
            "        \"InsuredPremiumDate\":\"2019-07-26\",\n" +
            "        \"SevenGSCode\":\"10342\",\n" +
            "        \"SumInsured\":\"1208200.00\",\n" +
            "        \"UGSCode\":\"07.7\",\n" +
            "        \"UVSCode\":\"16158011\"\n" +
            "      }, {\n" +
            "        \"CoverageTypeID\":\"MDamageNotAtFault\",\n" +
            "        \"Currency\":\"RUB\",\n" +
            "        \"GVSCode\":\"102\",\n" +
            "        \"InsuredPremium\":\"15183.00\",\n" +
            "        \"InsuredPremiumDate\":\"2019-07-26\",\n" +
            "        \"SevenGSCode\":\"10342\",\n" +
            "        \"SumInsured\":\"1208200.00\",\n" +
            "        \"UGSCode\":\"07.7\",\n" +
            "        \"UVSCode\":\"16158011\"\n" +
            "      }, {\n" +
            "        \"CoverageTypeID\":\"MTotalDamage\",\n" +
            "        \"Currency\":\"RUB\",\n" +
            "        \"GVSCode\":\"102\",\n" +
            "        \"InsuredPremium\":\"111377.00\",\n" +
            "        \"InsuredPremiumDate\":\"2019-07-26\",\n" +
            "        \"SevenGSCode\":\"10342\",\n" +
            "        \"SumInsured\":\"1208200.00\",\n" +
            "        \"UGSCode\":\"07.7\",\n" +
            "        \"UVSCode\":\"16158011\"\n" +
            "      }, {\n" +
            "        \"CoverageTypeID\":\"MTheft\",\n" +
            "        \"Currency\":\"RUB\",\n" +
            "        \"GVSCode\":\"102\",\n" +
            "        \"InsuredPremium\":\"28496.00\",\n" +
            "        \"InsuredPremiumDate\":\"2019-07-26\",\n" +
            "        \"SevenGSCode\":\"10342\",\n" +
            "        \"SumInsured\":\"1208200.00\",\n" +
            "        \"UGSCode\":\"07.7\",\n" +
            "        \"UVSCode\":\"16158011\"\n" +
            "      }, {\n" +
            "        \"CoverageTypeID\":\"MDamageByThirdPartyActions\",\n" +
            "        \"Currency\":\"RUB\",\n" +
            "        \"GVSCode\":\"102\",\n" +
            "        \"InsuredPremium\":\"7562.00\",\n" +
            "        \"InsuredPremiumDate\":\"2019-07-26\",\n" +
            "        \"SevenGSCode\":\"10342\",\n" +
            "        \"SumInsured\":\"1208200.00\",\n" +
            "        \"UGSCode\":\"07.7\",\n" +
            "        \"UVSCode\":\"16158011\"\n" +
            "      }, {\n" +
            "        \"CoverageTypeID\":\"MGlassDamage\",\n" +
            "        \"Currency\":\"RUB\",\n" +
            "        \"GVSCode\":\"102\",\n" +
            "        \"InsuredPremium\":\"2385.00\",\n" +
            "        \"InsuredPremiumDate\":\"2019-07-26\",\n" +
            "        \"SevenGSCode\":\"10342\",\n" +
            "        \"SumInsured\":\"1208200.00\",\n" +
            "        \"UGSCode\":\"07.7\",\n" +
            "        \"UVSCode\":\"16158011\"\n" +
            "      }, {\n" +
            "        \"CoverageTypeID\":\"MOtherDamage\",\n" +
            "        \"Currency\":\"RUB\",\n" +
            "        \"GVSCode\":\"102\",\n" +
            "        \"InsuredPremium\":\"5484.00\",\n" +
            "        \"InsuredPremiumDate\":\"2019-07-26\",\n" +
            "        \"SevenGSCode\":\"10342\",\n" +
            "        \"SumInsured\":\"1208200.00\",\n" +
            "        \"UGSCode\":\"07.7\",\n" +
            "        \"UVSCode\":\"16158011\"\n" +
            "      } ],\n" +
            "      \"DiscountKB\":0.0,\n" +
            "      \"EgnitionLockKeysCount\":\"2\",\n" +
            "      \"EngineHorsePower\":140,\n" +
            "      \"EnginePowerMeasured\":\"Horse power\",\n" +
            "      \"IsRegisteredInRF\":true,\n" +
            "      \"Make\":\"Kia\",\n" +
            "      \"MarkModelCode\":\"181020557\",\n" +
            "      \"Model\":\"Ceed\",\n" +
            "      \"PublicID\":\"pcrdev:3214\",\n" +
            "      \"RegistrationCountry\":\"643\",\n" +
            "      \"TransdekrBodyType\":\"Универсал\",\n" +
            "      \"TransdekrDoorsCount\":\"5\",\n" +
            "      \"TransdekrEngineType\":\"1.4 T-GDI (140.00 л.с.)\",\n" +
            "      \"TransdekrModification\":\"1.4T Luxe\",\n" +
            "      \"TransdekrTransmission\":\"Автоматическая\",\n" +
            "      \"UWCoefficientKA\":1.0,\n" +
            "      \"VIN\":\"4T1BE46K17U579178\",\n" +
            "      \"VehMakeForPrinitng\":\"Kia\",\n" +
            "      \"VehModelForPrinitng\":\"Ceed\",\n" +
            "      \"VehicleDocuments\":{\n" +
            "        \"DocumentNumber\":\"132456\",\n" +
            "        \"DocumentType\":\"1\",\n" +
            "        \"IssuanceDate\":\"2019-02-01\",\n" +
            "        \"PTSDuplicate\":false,\n" +
            "        \"PublicID\":\"pcrdev:2507\",\n" +
            "        \"SerialNumber\":\"1324\"\n" +
            "      },\n" +
            "      \"VehicleNumber\":\"А123АА23\",\n" +
            "      \"VehicleTypeID\":\"002\",\n" +
            "      \"VehicleUsagePurpose\":\"1\",\n" +
            "      \"Year\":2019\n" +
            "    },\n" +
            "    \"Offering\":\"MAutoProtection\",\n" +
            "    \"Owner\":{\n" +
            "      \"Address\":{\n" +
            "        \"AddressType\":\"Registration\",\n" +
            "        \"Area\":\"Шарлыкский р-н\",\n" +
            "        \"Country\":\"643\",\n" +
            "        \"Locality\":\"Шарлык с.\",\n" +
            "        \"PostalCode\":\"461000\",\n" +
            "        \"Region\":\"Оренбургская обл.\",\n" +
            "        \"Street\":\"Ломоносова\",\n" +
            "        \"StreetType\":\"Вал\"\n" +
            "      },\n" +
            "      \"ContactDocumentList\":[ {\n" +
            "        \"DocNumber\":\"326237\",\n" +
            "        \"DocStartDate\":\"2018-09-20\",\n" +
            "        \"DocumentType\":\"12\",\n" +
            "        \"Issued\":\"edl\",\n" +
            "        \"PublicID\":\"pcrdev:2419\",\n" +
            "        \"Serie\":\"4518\",\n" +
            "        \"SubdivisionCode\":\"287-986\"\n" +
            "      } ],\n" +
            "      \"ContactMail\":{\n" +
            "        \"Address\":\"asd2@gmail.com\",\n" +
            "        \"MailType\":\"Private\"\n" +
            "      },\n" +
            "      \"ContactPhone\":{\n" +
            "        \"PhoneNumber\":\"9151234567\",\n" +
            "        \"PhoneType\":\"Mobile\"\n" +
            "      },\n" +
            "      \"ContactSubtype\":\"1\",\n" +
            "      \"Country\":\"643\",\n" +
            "      \"DateOfBirth\":\"1976-05-12\",\n" +
            "      \"FirstName\":\"КАР\",\n" +
            "      \"FullName\":\"КАРЫЧ КАР КАРЫЧ\",\n" +
            "      \"Gender\":\"Male\",\n" +
            "      \"LastName\":\"КАРЫЧ\",\n" +
            "      \"MiddleName\":\"КАРЫЧ\",\n" +
            "      \"PublicID\":\"pcrdev:54406\",\n" +
            "      \"Resident\":\"1\"\n" +
            "    },\n" +
            "    \"PledgedProperty\":\"0\",\n" +
            "    \"PolicyNumber\":\"000000515\",\n" +
            "    \"PolicyNumberBSO\":\"000000515\",\n" +
            "    \"PolicyPublicID\":\"pcrdev:2007\",\n" +
            "    \"PolicySeries\":\"06621023010301\",\n" +
            "    \"PolicySeriesBSO\":\"06621023010301\",\n" +
            "    \"PolicySuffix\":\"1\",\n" +
            "    \"PolicySystemPeriodID\":\"2607\",\n" +
            "    \"Product\":\"MKASKO\",\n" +
            "    \"Region\":62,\n" +
            "    \"SKKCode\":16208330,\n" +
            "    \"SalesChannelType\":0,\n" +
            "    \"StartDate\":\"2019-07-25T21:00:00.000Z\",\n" +
            "    \"Status\":\"Scheduled\",\n" +
            "    \"SumInsured\":\"1208200.00\",\n" +
            "    \"SumInsuredTotal\":\"1208200.00\",\n" +
            "    \"SysOp\":\"I\"\n" +
            "  }\n" +
            "}\t\n";
}
