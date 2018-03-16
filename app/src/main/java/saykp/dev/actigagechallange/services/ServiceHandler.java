package saykp.dev.actigagechallange.services;

import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by user on 24-11-2017.
 */

public class ServiceHandler {
    private static final String TAG = "ServiceHandler";
    Process process;
    public boolean networkStatus;
    static ServiceHandler serviceHandler = null;



    /**
     * Generic function to get instance of this class.
     *
     * @return instance of this class.
     */
    public static ServiceHandler getInstance() {
        if (serviceHandler == null) {
            serviceHandler = new ServiceHandler();
        }
        return serviceHandler;
    }
//    public JSONObject contactServerWithPOST(String apiUrl, JSONObject requestObject,String token) {
//        Log.e(TAG, "POST call URL : " + apiUrl);
////        Log.e(TAG, "POST call request : " + requestObject.toString());
//        try {
//            URL url = new URL( apiUrl);
//            Log.e(TAG, "Final URL : " + url);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setDoOutput(true);
//            connection.setDoInput(true);
////            connection.addRequestProperty("Authorization", "bearer " + getToken());
//            connection.setConnectTimeout(10000);
//            connection.setRequestProperty("Content-Type", "application/json");
//                connection.setRequestProperty("Token",token);
//            connection.connect();
//            OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
//            if (requestObject!=null) {
//                outputStream.write(requestObject.toString().getBytes());
//            }
//            outputStream.flush();
//            outputStream.close();
//            if (connection.getResponseCode() == 200) {
//                Log.e(TAG, "status 200");
//                InputStream inputStream = connection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                String string;
//                StringBuilder stringBuilder = new StringBuilder();
//                while ((string = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(string);
//                }
//                Log.d(TAG, "POST 200 Response : " + stringBuilder.toString());
//                return new JSONObject(String.valueOf(stringBuilder));
//            } else {
//                Log.e(TAG, "status not 200 ::" + connection.getResponseMessage());
//                InputStream inputStream = connection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                String string;
//                StringBuilder stringBuilder = new StringBuilder();
//                while ((string = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(string);
//                }
//                Log.e(TAG, "POST response : " + stringBuilder.toString());
//                return new JSONObject();
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return new JSONObject();
//        } catch (ProtocolException pe) {
//            pe.printStackTrace();
//            return new JSONObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new JSONObject();
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return new JSONObject();
//        }
//    }
    public JSONObject contactServerWithGET(final String url) throws IOException, JSONException {
        URL obj = new URL(url);
        JSONObject jsonObject=new JSONObject("{\"status\":\"false\"}");

        StringBuffer response=new StringBuffer();;
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(30000);
//        if (token!=null){
//            con.setRequestProperty("Token",token);
//            con.setRequestProperty("Content-Type", "application/json");
//        }
//        con.setRequestProperty("User-Agent", "Test");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            jsonObject=new JSONObject(String.valueOf(response));
            return  jsonObject;
        } else {
            System.out.println("GET request not worked");
            return jsonObject;
        }

    }

}
