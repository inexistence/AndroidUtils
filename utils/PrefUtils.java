package utils;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class PrefUtils {
	private static final WeakHashMap<String, PrefUtils> sCachePrefUtils = new WeakHashMap<String, PrefUtils>();
	
	public static String DEFAULT_NAME = "default";
	
	public static String DEFAULT_STRING = "";
	public static int DEFAULT_INT = 0;
	public static long DEFAULT_LONG = 0L;
	public static float DEFAULT_FLOAT = 0F;
	public static boolean DEFAULT_BOOLEAN = false;
	
	private String mName = DEFAULT_NAME;
	private SharedPreferences mPref;

	public PrefUtils(Context ctx, String name) {
		SharedPreferences pref = null;
		if (DEFAULT_NAME.equals(name) || TextUtils.isEmpty(name)) {
			pref = PreferenceManager.getDefaultSharedPreferences(ctx);
		} else {
			pref = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
			mName = name;
		}
		mPref = pref;
	}

	public static PrefUtils getInstance(Context ctx, String name) {
		PrefUtils prefUtils = sCachePrefUtils.get(name);
		if (prefUtils == null) {
			prefUtils = new PrefUtils(ctx, name);
			sCachePrefUtils.put(name, prefUtils);
		}
		return prefUtils;
	}

	public static PrefUtils getInstance(Context ctx) {
		return getInstance(ctx, DEFAULT_NAME);
	}
	
	/**
	 * <b>Attention : </b>only boolean, string, int, float, double, long.
	 *         if string set wanted, use {@link #putStringSet(String, Set)}
	 * @param key
	 * @param obj only boolean, string, int, float, double, long
	 */
	public void put(String key, Object obj) {
		if (obj instanceof Boolean) {
			mPref.edit().putBoolean(key, (Boolean) obj).commit();
		} else if (obj instanceof String) {
			mPref.edit().putString(key, ((String) obj)).commit();
		} else if (obj instanceof Integer) {
			mPref.edit().putInt(key, (Integer) obj).commit();
		} else if (obj instanceof Float) {
			mPref.edit().putFloat(key, (Float) obj).commit();
		} else if (obj instanceof Double) {
			mPref.edit().putFloat(key, (Float) obj).commit();
		} else if (obj instanceof Character) {
			mPref.edit().putString(key, (Character.toString((Character) obj)))
					.commit();
		} else if (obj instanceof Long) {
			mPref.edit().putLong(key, (Long) obj).commit();
		}
	}
	
	public void putStringSet(String key, Set<String> value) {
		mPref.edit().putStringSet(key, value).commit();
	}
	
	public SharedPreferences getSharedPreferences() {
		return mPref;
	}
	
	public String getName() {
		return mName;
	}
	
	public String getString(String key) {
		return mPref.getString(key, DEFAULT_STRING);
	}

	public int getInt(String key) {
		return mPref.getInt(key, DEFAULT_INT);
	}

	public boolean getBoolean(String key) {
		return mPref.getBoolean(key, DEFAULT_BOOLEAN);
	}

	public float getFloat(String key) {
		return mPref.getFloat(key, DEFAULT_FLOAT);
	}
	
	public long getLong(String key) {
		return mPref.getLong(key, DEFAULT_LONG);
	}

	public String getString(String key, String defaultValue) {
		return mPref.getString(key, defaultValue);
	}

	public int getInt(String key, int defaultValue) {
		return mPref.getInt(key, defaultValue);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return mPref.getBoolean(key, defaultValue);
	}

	public float getFloat(String key, float defaultValue) {
		return mPref.getFloat(key, defaultValue);
	}
	
	public long getLong(String key, long defaultValue) {
		return mPref.getLong(key, defaultValue);
	}
	
	public Set<String> getStringSet(String key, Set<String> defaultValue) {
		return mPref.getStringSet(key, defaultValue);
	}
	
	public Map<String, ?> getAll() {
		return mPref.getAll();
	}
	
	public boolean contains(String name) {
		return mPref.contains(name);
	}

	public void remove(String key) {
		Editor editor = mPref.edit();
		editor.remove(key);
		editor.commit();
	}

	public void clear() {
		Editor editor = mPref.edit();
		editor.clear();
		editor.commit();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof PrefUtils && o !=null && ((PrefUtils) o).getName().equals(this.getName())) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return getName().hashCode();
	}

}
