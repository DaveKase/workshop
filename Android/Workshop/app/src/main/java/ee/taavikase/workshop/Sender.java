package ee.taavikase.workshop;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Sender extends AsyncTask<String, Void, String> {
    private static final String SERVER_URL = "http://192.168.1.173:8080/workshop/receiveClick";
    private String mUserId = "Taavi Kase";
    private static final String JSON_USER_ID = "user_id";
    private static final String JSON_BTN_NAME = "btn_name";
    private static final String TAG = "Sender";

    @Override
    protected String doInBackground(String... params) {
        Log.e(TAG, "doInBackground started");
        mUserId = checkForWhiteSpace();

        try{
            String content = createJsonString(params);
            HttpURLConnection httpURLConnection = createConnection();
            output(httpURLConnection, content);
            int httpResult = httpURLConnection.getResponseCode();

            if (httpResult != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, httpResult + " Error: " + httpURLConnection.getResponseMessage());
            }

            httpURLConnection.disconnect();
            return String.valueOf(httpResult);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String checkForWhiteSpace() {
        if(mUserId.contains(" ")) {
            String[] userIDs = mUserId.split(" ");
            mUserId = "";

            StringBuilder builder = new StringBuilder();
            for (String userID : userIDs) {
                builder.append(userID);
            }

            mUserId = builder.toString();
        }

        return mUserId;
    }

    private String createJsonString(String... params) throws JSONException {
        JSONObject jObject = new JSONObject();
        jObject.put(JSON_USER_ID, mUserId);
        jObject.put(JSON_BTN_NAME, params[0]);
        return jObject.toString();
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
        outStream.flush();
        outStream.close();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("TAG", result);
    }
}
