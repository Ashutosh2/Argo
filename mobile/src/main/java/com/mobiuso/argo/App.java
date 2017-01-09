package com.mobiuso.argo;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;

import org.artoolkit.ar.base.assets.AssetHelper;

/**
 * Created by ashutosh on 06/01/17.
 */

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private static Application sInstance;

    // Anywhere in the application where an instance is required, this method
    // can be used to retrieve it.
    public static Application getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        ((App) sInstance).initializeInstance();
    }

    // Here we do one-off initialisation which should apply to all activities
    // in the application.
    protected void initializeInstance() {

        PreferenceManager.setDefaultValues(this, org.artoolkit.ar.base.R.xml.preferences, false);

        // Unpack assets to cache directory so native library can read them.
        // N.B.: If contents of assets folder changes, be sure to increment the
        // versionCode integer in the AndroidManifest.xml file.
        AssetHelper assetHelper = new AssetHelper(getAssets());
        assetHelper.cacheAssetFolder(getInstance(), "Data");
        assetHelper.cacheAssetFolder(getInstance(), "DataNFT");
    }
}
