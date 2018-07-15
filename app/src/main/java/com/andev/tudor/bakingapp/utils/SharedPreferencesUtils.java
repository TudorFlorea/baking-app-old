package com.andev.tudor.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    public static int getIntFromSharedPrefsForKey(String key, Context context) {

        int selectedValue = 0;

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        selectedValue = sharedPreferences.getInt(key, 0);

        return selectedValue;
    }

    public static boolean setIntToSharedPrefsForKey(String key, int value, Context context) {

        boolean savedSuccesfuly = false;

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        try {
            editor.putInt(key, value);
            editor.apply();
            savedSuccesfuly = true;
        } catch (Exception e) {
            e.printStackTrace();
            savedSuccesfuly = false;
        }

        return savedSuccesfuly;
    }

}
