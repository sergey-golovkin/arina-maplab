package arina.maplab.processors.functions.scc;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class get_field_value extends MapLibraryFunctionProcessor {

    public get_field_value(IMapComponentDefinition definition, Integer growable) {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception {
        // Код справочника, по которому осуществляется поиск
        IMapValue dict_code = computeInputParameter(0, context);
        // Наименование колонки, значение из которой требуется найти
        IMapValue map_field = computeInputParameter(1, context);
        // Строковое представление ключ-значение, по которым осуществляется поиск
        IMapValue remap_field_id = computeInputParameter(2, context);

        if (dict_code.isNull())
            return MapValue.NULL;
        String spath = "http://localhost:8080/transform/";
        StringBuilder sb = new StringBuilder();
        URL url = new URL(spath);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "text/html");
        conn.setRequestProperty("Charset", "utf-8");
        conn.setRequestProperty("DictionaryCode", dict_code.getValue(String.class));
        conn.setRequestProperty("MapField", map_field.getValue(String.class));
        conn.setDoOutput(true);
        String value = remap_field_id.getValue(String.class);
        JSONObject object = new JSONObject(value);
        OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
        wr.write(object.toString());
        wr.flush();
        wr.close();
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP Error code : "
                    + conn.getResponseCode());
        }
        InputStreamReader in = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(in);
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        conn.disconnect();
        in.close();

        return new MapValue(this, sb.toString());
    }
}
