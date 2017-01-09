package com.mobiuso.argo.bundle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by dhavalnagar on 03/02/15.
 */
public class DownloadFileAsync extends AsyncTask<String, String, String> {

    private static final String TAG = "DOWNLOADFILE";

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private PostDownload callback;
    private Activity context;
    private FileDescriptor fd;
    private File file;
    private String downloadLocation;
    ProgressDialog progressDialog;

    public DownloadFileAsync(String downloadLocation, Activity context, PostDownload callback) {
        this.context = context;
        this.callback = callback;
        this.downloadLocation = downloadLocation;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... aurl) {
        int count;

        try {
            URL url = new URL(aurl[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            int lenghtOfFile = connection.getContentLength();
            Log.d(TAG, "Length of the file: " + lenghtOfFile);

            InputStream input = new BufferedInputStream(url.openStream());
            file = new File(downloadLocation);
            FileOutputStream output = new FileOutputStream(file); //context.openFileOutput("content.zip", Context.MODE_PRIVATE);
            Log.d(TAG, "file saved at " + file.getAbsolutePath());
            fd = output.getFD();

            byte data[] = new byte[2048];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                output.write(data, 0, count);
            }

            // output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
        }
        return null;

    }

    protected void onProgressUpdate(final String... progress) {
        //Log.d(TAG,progress[0]);
    }

    @Override
    protected void onPostExecute(String unused) {
        if (callback != null) callback.downloadDone(file);

    }

    public static interface PostDownload {
        void downloadDone(File fd);
    }
}