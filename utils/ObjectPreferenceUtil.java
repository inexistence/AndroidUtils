package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.List;

public class ObjectPreferenceUtil {
	private SharedPreferences mPref;
    private String DEFAULT_VALUE = new JsonObject().toString();
    private GsonBuilder gb;

    public ObjectPreferenceUtil(Context context) {
        mPref = PreferenceManager.getDefaultSharedPreferences(context);
        gb = new GsonBuilder();
    }

    public <E> void put(String key, E obj) {
        mPref.edit().putString(key, gb.create().toJson(obj)).commit();
    }

    public <E> E get(String key, Class<E> clazz) {
        String jsonStr = mPref.getString(key, DEFAULT_VALUE);
        return gb.create().fromJson(jsonStr, clazz);
    }

    public <E> E getList(String key, Type type) {
        String jsonStr = mPref.getString(key, DEFAULT_VALUE);
        if (jsonStr.equals(DEFAULT_VALUE)) jsonStr = "[]";
        return gb.create().fromJson(jsonStr, type);
    }

    public <E> void remove(String key) {
        mPref.edit().remove(key).commit();
    }


    public <E> void putClass(E obj) {
        Class<?> clazz = obj.getClass();
        mPref.edit().putString(clazz.getName(), gb.create().toJson(obj)).commit();
    }

    public <E> E getClass(Class<E> clazz) {
        String jsonStr = mPref.getString(clazz.getName(), DEFAULT_VALUE);
        if (jsonStr.equals(DEFAULT_VALUE)) return null;
        return gb.create().fromJson(jsonStr, clazz);
    }

    public <E> void removeClass(Class<E> clazz) {
        mPref.edit().remove(clazz.getName()).commit();
    }
}
