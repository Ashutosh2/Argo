package com.mobiuso.argo.bundle;

import android.os.AsyncTask;
import android.util.Log;

import com.mobiuso.argo.config.Config;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ManifestHandler extends AsyncTask<String, String, String> {

    HttpURLConnection urlConnection;
    ArrayList filesList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> bundle;


    public ManifestHandler(PostDownload postDownload) {
        this.postDownload = postDownload;
    }

    @Override
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(Config.MANIFEST_FILE_LOCATION);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }


        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        Manifest manifest = Manifest.parse(result);
        for (int i = 0; i < manifest.bundles.length; i++) {
            bundle = new HashMap<String, String>();
            bundle.put("id", String.valueOf(manifest.bundles[i].id));
            bundle.put("name", manifest.bundles[i].name);
            bundle.put("file", manifest.bundles[i].file);
            bundle.put("markerType", manifest.bundles[i].markerType);
            bundle.put("fileLink", manifest.bundles[i].fileLink);
            bundle.put("contentLink", manifest.bundles[i].contentLink);
            filesList.add(bundle);
            Log.d("file", filesList.get(i).toString());
        }
        postDownload.downloadDone(filesList);

    }

    public final PostDownload postDownload ;

    public static interface PostDownload {
        void downloadDone(ArrayList<HashMap<String, String>> list);
    }

}