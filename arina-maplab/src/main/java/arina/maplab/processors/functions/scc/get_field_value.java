package arina.maplab.processors.functions.scc;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class get_field_value extends MapLibraryFunctionProcessor {

    @Value("${server.port}")
    private String serverPort;

    public get_field_value(IMapComponentDefinition definition, Integer growable) {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception {
        // Код справочника, по которому осуществляется поиск
        IMapValue dictionary_id = computeInputParameter(0, context);
        // Наименование колонки, значение из которой требуется найти
        IMapValue search_field_id = computeInputParameter(1, context);
        // Строковое представление ключ-значение в формате json, по которым осуществляется поиск
        IMapValue criteria = computeInputParameter(2, context);

        if (dictionary_id.isNull())
            return MapValue.NULL;
        if (serverPort == null) {
            serverPort = "8080";
        }
        String spath = "http://localhost:" + serverPort + "/transform/";
        StringBuilder sb = new StringBuilder();
        URL url = new URL(spath);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/html");
        conn.setRequestProperty("DictionaryCode", dictionary_id.getValue(String.class));
        conn.setRequestProperty("MapField", search_field_id.getValue(String.class));
        conn.setDoOutput(true);
        String value = criteria.getValue(String.class);
        JSONObject object = new JSONObject(value);
        OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
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
