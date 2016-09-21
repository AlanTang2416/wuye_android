package com.base.baselibs.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 全局方法工具类
 */
public class PreferenceUtil {
	private static final String PREFERENCE_NAME = "atman_baiye_preference";
	public static final String PARM_US = "UserName";
	public static final String PARM_PW = "PassWord";
	public static final String PARM_USERID = "UserId";
	public static final String PARM_USER_KEY = "USER_KEY";
	public static final String PARM_USER_TOKEN = "USER_TOKEN";
	public static final String PARM_YUNXIN_TOKEN = "PARM_YUNXIN_TOKEN";
	public static final String PARM_YUNXIN_WRAN = "PARM_YUNXIN_WRAN";
	public static final String PARM_GESTURE_PW = "Gesture_PW";
	public static final String PARM_ISOPEN_GESTURE = "IsOpen_Gesture";
	public static final String PARM_GESTURE_ERROR = "Gesture_error";

	/**
	 * a method to save global data
	 * 
	 * @param mContext
	 * @param preferenceKey
	 *            key
	 * @param preferenceValue
	 *            value
	 */
	public static void savePreference(Context mContext, String preferenceKey, String preferenceValue) {
		SharedPreferences preference = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor edit = preference.edit();
		edit.putString(preferenceKey, preferenceValue);
		edit.commit();
	}

	public static void saveBoolPreference(Context mContext, String preferenceKey, boolean preferenceValue) {
		SharedPreferences preference = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor edit = preference.edit();
		edit.putBoolean(preferenceKey, preferenceValue);
		edit.commit();
	}

	public static void saveIntPreference(Context mContext, String preferenceKey, int preferenceValue) {
		SharedPreferences preference = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor edit = preference.edit();
		edit.putInt(preferenceKey, preferenceValue);
		edit.commit();
	}

	/**
	 * Save preference use map .
	 * 
	 * @function : save profiles to preferences .
	 * @param mContext
	 *            the m context
	 * @param nameAndValues
	 *            the name and values
	 */
	public static void savePreference(Context mContext, Map<String, String> nameAndValues) {
		SharedPreferences preference = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor edit = preference.edit();
		Set<String> set = nameAndValues.keySet();
		Iterator<String> iter = set.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			String val = nameAndValues.get(key);
			edit.putString(key, val);
		}
		edit.commit();
	}

	/**
	 * a method to get global data
	 * 
	 * @param context
	 * @param param
	 *            参数名
	 * @return
	 */
	public static String getPreferences(Context context, String param) {
		SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		return prefs.getString(param, "");
	}

	/**
	 * 默认为-1
	 * 
	 * @param context
	 * @param param
	 * @return
	 */
	public static int getIntPreferences(Context context, String param) {
		SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		return prefs.getInt(param, -1);
	}

	/**
	 * 默认为false
	 * 
	 * @param context
	 * @param param
	 * @return
	 */
	public static boolean getBoolPreferences(Context context, String param) {
		SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		return prefs.getBoolean(param, false);
	}

	/**
	 * 获取某个偏好设置, 默认0l
	 * 
	 * @param context
	 * @param param
	 *            参数名
	 * @return
	 */
	public static Long getLongPreferences(Context context, String param) {
		SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		return prefs.getLong(param, 0l);
	}

	/**
	 * 保存偏好设置
	 * 
	 * @param mContext
	 * @param preferenceKey
	 *            key
	 * @param preferenceValue
	 *            value
	 */
	public static void saveLongPreference(Context mContext, String preferenceKey, Long preferenceValue) {
		SharedPreferences preference = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor edit = preference.edit();
		edit.putLong(preferenceKey, preferenceValue);
		edit.commit();
	}

}