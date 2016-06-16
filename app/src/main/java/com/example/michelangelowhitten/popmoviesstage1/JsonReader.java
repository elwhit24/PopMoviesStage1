package com.example.michelangelowhitten.popmoviesstage1;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonReader {

    JSONObject json;
    String jsonText;

    public JsonReader() {
        this.json = new JSONObject();
        this.jsonText = "";
    }

    public JSONObject JsonRead(String url) throws IOException, JSONException {
        try {
            return readJsonFromUrl(url);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return readJsonFromUrl(url);
    }

    public String readIt(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int count;
        while ((count = reader.read()) != -1) {
            stringBuilder.append((char) count);
        }
        return stringBuilder.toString();
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            this.jsonText = readIt(bufferedReader);
            this.json = new JSONObject(jsonText);
            return json;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (final IOException e) {
                    Log.e("error w/ retrieval", "error closing stream", e);
                }
            }
        }
    }
}
