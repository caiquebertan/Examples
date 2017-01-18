package br.com.examples.caique.examplesproject.controller;

import android.app.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucasrodrigues on 06/12/16.
 */

public class Permissions {
    public static int ALL_PERMISSIONS;

    private Activity activity;

    public Permissions(Activity activity) {
        this.activity = activity;
    }

    public String[] getPermissions() {
        List<String> list = new ArrayList<String>();
        list.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        list.add(Manifest.permission.ACCESS_FINE_LOCATION);

        return list.toArray(new String[list.size()]);
    }

    public void requestAll() {
        ActivityCompat.requestPermissions(activity, getPermissions(), ALL_PERMISSIONS);
    }

    public boolean verifyAllPermissions() {
        for (String permission : getPermissions()) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    public void onResultVerify(int[] grantResults, OnPermissionsSend onPermissionsSend) {
        if (grantResults.length > 0 && verifyResult(grantResults)) {
            onPermissionsSend.onGranted();
        } else {
            onPermissionsSend.onDenied();
        }
    }

    private boolean verifyResult(int[] grantResults) {
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }

        return true;
    }

    public interface OnPermissionsSend {
        void onGranted();
        void onDenied();
    }
}