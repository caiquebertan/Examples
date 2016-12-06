package br.com.examples.caique.examplesproject.common.controller;

import android.app.Application;


public class App extends Application {

    private static App instance;
    private boolean mIsAirPlaneMode;

    @Override
    public void onCreate() {
        super.onCreate();
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        App.instance = this;
    }

    public static App getInstance() {
        return instance;
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    public void dispatchAirplaneModeChange(boolean booleanExtra) {
        mIsAirPlaneMode = booleanExtra;
    }

    public boolean isAirPlaneMode() {
        return mIsAirPlaneMode;
    }
}
