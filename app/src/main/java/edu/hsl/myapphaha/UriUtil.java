package edu.hsl.myapphaha;

import android.net.Uri;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/29.
 */
public class UriUtil {
    final String uriString = "http://japi.juhe.cn/joke/content/list.from?";
    final String APPKEY    = "35dff966e248deb39d23cc9988d55ffc";

    public UriUtil() {
    }

    private String getJson(String unixtime) {

        StringBuffer sb = null;
        try {
            sb = new StringBuffer();
            String uri = Uri.parse(uriString).buildUpon()
                    .appendQueryParameter("sort", "desc")
                    .appendQueryParameter("page", "")
                    .appendQueryParameter("pagesize", "10")
                    .appendQueryParameter("time", unixtime)
                    .appendQueryParameter("key", APPKEY)
                    .build().toString();
            URL               url           = new URL(uri);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.connect();
            InputStream    inputStream = urlConnection.getInputStream();
            BufferedReader reader      = new BufferedReader(new InputStreamReader(inputStream));
            String         strData;
            while ((strData = reader.readLine()) != null) {
                sb.append(strData);
            }
            inputStream.close();
            reader.close();
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public JsonBean getData(String unixtime) {
        String json = getJson(unixtime);
        return new Gson().fromJson(json, JsonBean.class);
    }

    public String getTime() {
        Date date = new Date();
        long time = date.getTime() / 1000;
        return String.valueOf(time);
    }
}
