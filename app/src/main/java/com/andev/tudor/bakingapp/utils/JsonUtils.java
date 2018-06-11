package com.andev.tudor.bakingapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    /**
     * function that returns a json object from a json formatted string or null.
     * @param rawJson
     * @return JSONObject or null
     */

    public static JSONObject jsonObjectFromString(String rawJson) {
        try {
            return new JSONObject(rawJson);
        } catch (JSONException jse) {
            jse.printStackTrace();
            return null;
        }
    }

    public static JSONArray jsonArrayFromString(String rawArray) {
        try {
            return new JSONArray(rawArray);
        } catch (JSONException jse) {
            jse.printStackTrace();
            return null;
        }
    }

}
