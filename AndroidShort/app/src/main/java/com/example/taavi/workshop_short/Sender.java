package com.example.taavi.workshop_short;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import java.net.URL;

public class Sender extends AsyncTask<String, Void, String> {
    private static final String TAG = "Sender";

    private String createJsonString(String... params) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", "Taavi lyhike");
        jsonObject.put("btn_name", params[0]);
        String json = jsonObject.toString();
        Log.e(TAG, "json, = " + json);
        return json;
    }

    private HttpURLConnection createConnection() throws IOException {
        URL url = new URL("");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.connect();
        return httpURLConnection;
    }

    private void output(HttpURLConnection httpURLConnection, String content) throws IOException {
        Log.e(TAG, "content = " + content);
        DataOutputStream outStream = new DataOutputStream(httpURLConnection.getOutputStream());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream, "UTF-8"));
        writer.write(content);
        writer.close();
        outStream.close();
    }

    private String getResponseContent(HttpURLConnection httpURLConnection) throws IOException, JSONException {
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();
        JSONObject jsonObject = new JSONObject(response.toString());
        Log.e(TAG, "response jsonObject = " + jsonObject.toString());

        return jsonObject.getString("clicked");
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String content = createJsonString(params);
            HttpURLConnection httpURLConnection = createConnection();
            output(httpURLConnection, content);
            int httpResult = httpURLConnection.getResponseCode();

            if (httpResult == HttpURLConnection.HTTP_OK) {
                String responseContent = getResponseContent(httpURLConnection);
                httpURLConnection.disconnect();
                return responseContent;
            } else {
                return httpResult + " Error: " + httpURLConnection.getResponseMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "exception: " + e.getMessage();
        }
    }
}
