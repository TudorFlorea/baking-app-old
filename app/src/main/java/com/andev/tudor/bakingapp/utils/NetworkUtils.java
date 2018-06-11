package com.andev.tudor.bakingapp.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {

    private static final String JSON_URL_STRING = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    /**
     * function that makes a HTTP request using the OkHttp library and returns the raw String response from the request
     * @param url - api call URL
     * @return String - the raw Json response from the HTTP request
     */

    private static String getResponseFromHttpUrl(URL url) {

        OkHttpClient client = new OkHttpClient();

        Request.Builder builder = new Request.Builder();
        builder.url(url);

        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }

    }

    public static String getRawJsonData() {
        return getResponseFromHttpUrl(buildUrlFromString(JSON_URL_STRING));
    }

    /**
     * function that turns a string in a URL object
     * @param s - URL string
     * @return a new URL object or throws a MalformedURLException and returns null
     */

    private static URL buildUrlFromString (String s) {
        try {
            return new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
