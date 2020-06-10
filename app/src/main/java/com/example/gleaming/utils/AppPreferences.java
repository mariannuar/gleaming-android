package com.example.gleaming.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private static AppPreferences preferences;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public final String PREFERENCES = "my_prefs";

    public static class Keys {
        public static final String NAME_STRING = "name";
        public static final String IS_LOGGED_IN = "is_logged_in";

    }

    private AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES, context.MODE_PRIVATE);
    }

    public static AppPreferences getInstance(Context context) {
        if (preferences == null)
            preferences = new AppPreferences(context);
        return preferences;
    }


    public void put(String key, String value) {
        doEdit();
        editor.putString(key, value);
        doCommit();
    }

    public void put(String key, int value) {
        doEdit();
        editor.putInt(key, value);
        doCommit();
    }

    public void put(String key, boolean value) {
        doEdit();
        editor.putBoolean(key, value);
        doCommit();
    }

    public void put(String key, float value) {
        doEdit();
        editor.putFloat(key, value);
        doCommit();
    }

    public void put(String key, double value) {
        doEdit();
        editor.putString(key, String.valueOf(value));
        doCommit();
    }

    public void put(String key, long value) {
        doEdit();
        editor.putLong(key, value);
        doCommit();
    }

    ///
    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }
    ///

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    ///
    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    ///
    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    ///
    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    public float getFloat(String key, long defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    ///
    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    public double getDouble(String key, double defValue) {
        try {
            return Double.valueOf(sharedPreferences.getString(key, String.valueOf(defValue)));

        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    public void clear() {
        doEdit();
        editor.clear();
        doCommit();
    }

    public void remove(String... keys) {
        doEdit();

        for (String key : keys)
            editor.remove(key);

        doCommit();

    }

    private void doEdit() {
        if (editor == null) editor = sharedPreferences.edit();
    }

    private void doCommit() {
        if (editor != null) {
            editor.commit();
            editor = null;
        }
    }
}

