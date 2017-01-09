package com.mobiuso.argo.bundle;

import com.google.gson.Gson;

/**
 * Created by apple on 06/01/17.
 */

public class Manifest {
    public int totalBundles;
    public Bundles[] bundles;

    class Bundles {
        public int id;
        public String name;
        public String file;
        public String markerType;
        public String fileLink;
        public String contentLink;
    }

    public static Manifest parse(String json) {
        Gson gson = new Gson();
        Manifest manifest = gson.fromJson(json,Manifest.class);
        return manifest;
    }
}