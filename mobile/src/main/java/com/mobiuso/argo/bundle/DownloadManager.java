package com.mobiuso.argo.bundle;

/**
 * Created by apple on 03/01/17.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;



public class DownloadManager {

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    public static final String TAG = DownloadManager.class.getName();

    private Activity context;
    volatile int count;
    volatile int mCount;
    ProgressDialog progressDialog;

    public DownloadManager(Activity context ,ArrayList<HashMap<String,String>> list) {
        this.context = context;
        this.count = 0;
        this.mCount = 0;
        this.startDownload(list);
    }

    public void startDownload(ArrayList<HashMap<String,String>> list) {
        Log.v("inside start download", "");

        try {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog = ProgressDialog.show(context, "", "Please Wait...", true);

                }
            });

            for (int i = 0; i < list.size(); i++, count++) {

                String url = list.get(i).get("fileLink");//baseUrl.concat(filesList.get(i).toString());
                Log.v("url :", url);
                String fileName = list.get(i).get("file");
                Log.v("fileName :", fileName);
                downloadAndUnzipContent(url, fileName);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void downloadAndUnzipContent(String url, String fileName) {
        String appPath = context.getApplicationInfo().dataDir;
        Log.d("apppath", appPath.toString());
        DownloadFileAsync download = new DownloadFileAsync(appPath + "/" + fileName, context, new DownloadFileAsync.PostDownload() {
            @Override
            public void downloadDone(File file) {
                Log.i(TAG, "file download completed");

                // check unzip file now
                Decompress decompress = new Decompress(context, file);
                decompress.unzip();
                if (++mCount == count) {
                    //progressDialog.dismiss();
                    //Intent intent = new Intent(context, ARMovieActivity.class);
                    //context.startActivity(intent);
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    });

                }

                Log.i(TAG, "file unzip completed");
            }
        });
        download.execute(url);
    }


}