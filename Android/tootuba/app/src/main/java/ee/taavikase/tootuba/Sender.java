package ee.taavikase.tootuba;

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
    private String mUserID ="Taavi";
    private static final String JSON_USER_ID = "user_id";
    private static final String JSON_BTN_NAME = "btn_name";
    private static final String TAG = "Sender";

    @Override
    protected String doInBackground(String... params) {
        Log.e(TAG, "doInBackground started");
        mUserID = checkForWhiteSpace();

        try {
            String content = createJsonString(params);
            HttpURLConnection httpurlConnection = createConnection();
            output(httpurlConnection, content);
            int httpResult = httpurlConnection.getResponseCode();

            if (httpResult != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, httpResult + " error " + httpurlConnection.getResponseMessage());
            }

            httpurlConnection.disconnect();
            return String.valueOf(httpResult);
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String checkForWhiteSpace() {
        if (mUserID.contains(" ")) {
            String[] userIDs = mUserID.split(" ");
            mUserID = "";

            StringBuilder builder = new StringBuilder();
            for (String userID : userIDs) {
                builder.append(userID);
            }

            mUserID = builder.toString();
        }

        return mUserID;
    }

    private String createJsonString(String... params) throws JSONException {
        JSONObject jObject = new JSONObject();
        jObject.put(JSON_USER_ID, mUserID);
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
        DataOutputStream outputStream = new DataOutputStream(httpURLConnection.getOutputStream());
        outputStream.writeBytes(content);
        outputStream.flush();
        outputStream.close();
    }
}
