package com.example.airpark.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.airpark.utils.Networking.NetworkHandler;

import org.json.JSONObject;

public class Utilities {

    private static Utilities shared = null;

    public static Utilities getInstance(){
        if (shared == null){
            shared = new Utilities();
        }

        return shared;
    }

    public void saveJsonObject(JSONObject json, Context c){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("user", json.toString());
        editor.commit();
    }

    public JSONObject fetchJsonObject(Context c, String key) throws Exception{

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(c);
        try {
            return new JSONObject(pref.getString(key, null));
        }catch (Exception e){
            throw e;
        }
    }
}
