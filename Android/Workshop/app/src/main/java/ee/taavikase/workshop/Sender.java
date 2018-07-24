package ee.taavikase.workshop;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Sender extends AsyncTask<String, Void, String> {
    private static final String TAG = "Sender";
    private static final String SERVER_URL = "http://192.168.1.173:8080/workshop/receiveClick";
    private static final String JSON_USER_ID = "user_id";
    private static final String JSON_BTN_NAME = "btn_name";
    private static final String JSON_CLICKED = "clicked";

    // mUserId ei tohi sisaldada täpitähti, kuna seda kasutatakse ka serveris URLi osana
    private String mUserId = "Taavi Kase";

    private String checkForWhiteSpace() {
        if (mUserId.contains(" ")) {
            String[] userIDs = mUserId.split(" ");
            mUserId = "";

            StringBuilder builder = new StringBuilder();

            for (String userId : userIDs) {
                builder.append(userId);
            }

            mUserId = builder.toString();
        }

        return mUserId;
    }

    private String createJsonString(String... params) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_USER_ID, mUserId);
        jsonObject.put(JSON_BTN_NAME, params[0]);
        Log.e(TAG, "JSON = " + jsonObject.toString());
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
        outStream.writeBytes(content);
        Log.e(TAG, "content = " + content);
        outStream.flush();
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
        return jsonObject.getString(JSON_CLICKED);
    }

    @Override
    protected String doInBackground(String... params) {
        Log.e(TAG, "doInBackground");
        mUserId = checkForWhiteSpace();

        try {
            String content = createJsonString(params);
            HttpURLConnection httpURLConnection = createConnection();
            output(httpURLConnection, content);
            int httpResult = httpURLConnection.getResponseCode();

            if (httpResult != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, httpResult + " Error: " + httpURLConnection.getResponseMessage());
                return "";
            } else {
                String responseContent = getResponseContent(httpURLConnection);
                httpURLConnection.disconnect();
                return responseContent;
            }
        } catch (Exception e) {
            Log.e(TAG, "exception: " + e.getMessage());
            return "";
        }
    }
}
