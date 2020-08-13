package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class dict_transform extends MapLibraryFunctionProcessor {
    public dict_transform(IMapComponentDefinition definition, Integer growable) {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception {
        IMapValue dict_code = computeInputParameter(0, context);
        IMapValue map_field_ids = computeInputParameter(1, context);
        IMapValue map_field_values = computeInputParameter(2, context);
        IMapValue remap_field_id = computeInputParameter(3, context);

        if (dict_code.isNull())
            return MapValue.NULL;
        String spath = "http://localhost:8080/transform/";
        StringBuilder sb = new StringBuilder();
        URL url = new URL(spath);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("DictionaryCode", dict_code.getValue(String.class));
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

        return new MapValue(this, sb.toString());

    }
}
