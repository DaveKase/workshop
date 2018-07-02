package ee.taavikase.workshop;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Sender extends AsyncTask<String, Void, String> {
    private static final String SERVER_URL = "http://192.168.1.173:8080/workshop/receiveClick";
    private static final String USER_ID = "Taavi Kase";
    private static final String JSON_USER_ID = "user_id";
    private static final String JSON_BTN_NAME = "btn_name";
    private static final String TAG = "Sender";

    @Override
    protected String doInBackground(String... strings) {
        Log.e(TAG, "doInBackground started");

        HttpURLConnection httpURLConnection = null;
        StringBuilder data = new StringBuilder();

        try {
            JSONObject jObject = new JSONObject();
            jObject.put(JSON_USER_ID, USER_ID);
            jObject.put(JSON_BTN_NAME, strings[0]);
            String content = jObject.toString();
            Log.e(TAG, content);
            httpURLConnection = (HttpURLConnection) new URL(SERVER_URL).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(content);
            wr.flush();
            wr.close();

            InputStream in = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in);

            int inputStreamData = inputStreamReader.read();
            while (inputStreamData != -1) {
                char current = (char) inputStreamData;
                inputStreamData = inputStreamReader.read();
                data.append(current);
            }

            Log.e(TAG, data.toString());
            return data.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return data.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
    }
}
