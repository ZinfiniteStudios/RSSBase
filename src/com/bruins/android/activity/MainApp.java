package com.bruins.android.activity;

import com.google.inject.Inject;
import com.google.inject.Injector;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.File;
import java.util.Map;

import roboguice.RoboGuice;

/**
 * Created by david.hodge on 6/26/13.
 */
public class MainApp extends Application {

    public static String TAG = "rssbase-android";
    public static final String XMLNS = "http://rssbase-android/schema";

//    @Inject Datastore mDataStore;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        Injector i = RoboGuice.getBaseApplicationInjector(this);
//        mDataStore = i.getInstance(Datastore.class);
//        try {
//            int newVersionCode = getPackageManager()
//                    .getPackageInfo(getPackageName(), 0).versionCode;
//            int oldVersionCode = mDataStore.getVersion();
//            if (oldVersionCode != 0 && oldVersionCode != newVersionCode) {
//                onVersionUpdate(oldVersionCode, newVersionCode);
//            }
//            mDataStore.persistVersion(newVersionCode);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    private void onVersionUpdate(int oldVersionCode, int newVersionCode) {
        //this method is called when the version code changes, use comparison operators to control migration
    }
}
