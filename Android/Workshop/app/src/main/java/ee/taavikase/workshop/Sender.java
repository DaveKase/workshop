package ee.taavikase.workshop;

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
    private static final String SERVER_URL = "";
    private static final String USER_ID = "Taavi";
    private static final String JSON_USER_ID = "user_id";
    private static final String JSON_BTN_NAME = "btn_name";
    private static final String JSON_CLICKED = "clicked";

    private String createJsonString(String... params) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_USER_ID, USER_ID);
        jsonObject.put(JSON_BTN_NAME, params[0]);
        return jsonObject.toString();
    }

    private HttpURLConnection createConnection() throws IOException {
        URL url = new URL(SERVER_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.connect();
        return httpURLConnection;
    }

    private void output(HttpURLConnection httpURLConnection, String content) throws IOException {
        DataOutputStream outStream = new DataOutputStream(httpURLConnection.getOutputStream());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream, "UTF-8"));
        writer.write(content);
        writer.close();
        outStream.close();
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

        return jsonObject.getString(JSON_CLICKED);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String content = createJsonString(params);
            Log.e(TAG, "json, = " + content);
            HttpURLConnection httpURLConnection = createConnection();
            output(httpURLConnection, content);
            int httpResult = httpURLConnection.getResponseCode();

            if (httpResult == HttpURLConnection.HTTP_OK) {
                String responseContent = getResponseContent(httpURLConnection);
                Log.e(TAG, "response jsonObject = " + responseContent);
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
