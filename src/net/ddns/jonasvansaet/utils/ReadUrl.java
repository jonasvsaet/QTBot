package net.ddns.jonasvansaet.utils;

import com.google.api.client.util.Base64;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Executable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


public class ReadUrl {

    public static String readUrl (String myUrl){
        URLConnection urlConn = null;
        InputStreamReader inputStreamReader = null;
        String result = "";

        try{
            URL url = new URL(myUrl);
            urlConn = url.openConnection();
            if (urlConn != null){
                urlConn.setReadTimeout(60 * 1000);
            }
            if (urlConn != null && urlConn.getInputStream() != null) {
                inputStreamReader = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                if (bufferedReader != null){
                    int i;
                    while((i = bufferedReader.read()) != -1){
                        result += (char) i;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }

    public static String readUrlWithAuth (String urlString, String user, String pass){
        String result = "";
        try {
            URL url = new URL(urlString);
            String auth = user + ":" + pass;
            String authEncoded = javax.xml.bind.DatatypeConverter.printBase64Binary(auth.getBytes());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Basic " + authEncoded);

            InputStream inputStream = connection.getInputStream();

            int i;
            while((i = inputStream.read()) != -1){
                result += (char) i;
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return result;

    }

    public static String readUrlWithParameters (String urlString, HashMap<String, String> parameters){
        String result = "";
        try {
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            for(Map.Entry<String, String> entry : parameters.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                //String encoded = javax.xml.bind.DatatypeConverter.printBase64Binary(value.getBytes());

                connection.setRequestProperty(key, value);

            }

            InputStream inputStream = connection.getInputStream();

            int i;
            while ((i = inputStream.read()) != -1){
                result += (char) i;
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return result;
    }


}
