package br.com.examples.caique.examplesproject.common.controller;


import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager
{
	private static final String PREF_NAME = "Pref";
	private static final String KEY_TOKEN = "token";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASS = "password";
	private static final String KEY_IS_IN_THE_CITY ="is_in_the_city";
	private static final String KEY_SHOW_IMPROVE_LOCATION = "show_improve_location";
	private static final String KEY_LOCATION_PERMISSION_GRANTED = "location_permission_granted";

	public static SharedPreferences get() {
		Context context = App.getInstance().getApplicationContext();
		return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}

	public static String getAccessToken() {
		return get().getString(KEY_TOKEN, null);
	}

	public static void saveAccessToken(String token) {
		get().edit().putString(KEY_TOKEN, token).apply();
	}

	public static void saveUsername(String username) {
		get().edit().putString(KEY_USERNAME, username).apply();
	}

	public static String getUsername() {
		return get().getString(KEY_USERNAME, null);
	}

	public static void savePassword(String username) {
		get().edit().putString(KEY_PASS, username).apply();
	}

	public static String getPassword() {
		return get().getString(KEY_PASS, null);
	}

	public static void setShowImproveLocation(boolean show) {
		get().edit().putBoolean(KEY_SHOW_IMPROVE_LOCATION, show).apply();
	}
	
	public static boolean getShowImproveLocation() {
		return get().getBoolean(KEY_SHOW_IMPROVE_LOCATION, true);
	}
	
	public static boolean isInTheCity() {
		return get().getBoolean(KEY_IS_IN_THE_CITY, false);
	}
	
	public static void setIsInTheCity(boolean isInTheCity) {
		get().edit().putBoolean(KEY_IS_IN_THE_CITY, isInTheCity).apply();
	}

	public static boolean getLocationPermissionGranted(){
		return get().getBoolean(KEY_LOCATION_PERMISSION_GRANTED, true);
	}
	
	public static void setLocationPermissionGranted(boolean granted) {
		get().edit().putBoolean(KEY_LOCATION_PERMISSION_GRANTED, granted).apply();
	}
	
}
