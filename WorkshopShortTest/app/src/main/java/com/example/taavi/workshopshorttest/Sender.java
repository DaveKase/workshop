package com.example.taavi.workshopshorttest;

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
        jsonObject.put("user_id", "MSI_22");
        jsonObject.put("btn_name", params[0]);
        return jsonObject.toString();
    }

    private HttpURLConnection createConnection() throws IOException {
        URL url = new URL("http://192.168.211.48:8080/Workshop/receiveClick");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.connect();
        return httpURLConnection;
    }

    private void output(HttpURLConnection httpURLConnection, String content) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(httpURLConnection.getOutputStream());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        writer.write(content);
        writer.close();
        outputStream.close();
    }

    private String getResponseContent(HttpURLConnection httpURLConnection) throws IOException, JSONException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }

        bufferedReader.close();
        JSONObject jsonObject = new JSONObject(response.toString());
        Log.e(TAG, "response jsonObject = " + jsonObject.toString());

        return jsonObject.getString("clicked");
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String content = createJsonString(params);
            Log.e(TAG, "json = " + content);
            HttpURLConnection httpURLConnection = createConnection();
            output(httpURLConnection, content);
            int httpResult = httpURLConnection.getResponseCode();

            if(httpResult == HttpURLConnection.HTTP_OK) {
                String responseContent = getResponseContent(httpURLConnection);
                httpURLConnection.disconnect();
                Log.e(TAG, "content = " + responseContent);

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
