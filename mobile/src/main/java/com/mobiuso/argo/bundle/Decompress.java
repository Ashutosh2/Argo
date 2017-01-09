package com.mobiuso.argo.bundle;

import android.content.Context;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author dhaval nagar
 */
public class Decompress {
    private File _zipFile;
    private InputStream _zipFileStream;
    private Context context;
    private String cacheDir = "";
    private static final String TAG = "UNZIPUTIL";

    public Decompress(Context context, File zipFile) {
        _zipFile = zipFile;
        this.context = context;

        cacheDir = context.getCacheDir().getPath() + "/DataNFT";
        _dirChecker("");
    }

    public Decompress(Context context, InputStream zipFile) {
        _zipFileStream = zipFile;
        this.context = context;

        _dirChecker("");
    }

    public void unzip() {
        try  {
            Log.i(TAG, "Starting to unzip");
            InputStream fin = _zipFileStream;
            if(fin == null) {
                fin = new FileInputStream(_zipFile);
            }
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.v(TAG, "Unzipping " + ze.getName());

                if(ze.isDirectory()) {
                    Log.v("cacheDir",cacheDir);
                    _dirChecker(cacheDir + "/" + ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(new File(cacheDir, ze.getName()));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int count;

                    // reading and writing
                    while((count = zin.read(buffer,0,buffer.length)) != -1)
                    {
                        baos.write(buffer, 0, count);
                        byte[] bytes = baos.toByteArray();
                        fout.write(bytes);
                        baos.reset();
                    }

                    fout.close();
                    zin.closeEntry();
                }

            }
            zin.close();
            Log.i(TAG, "Finished unzip");
        } catch(Exception e) {
            Log.e(TAG, "Unzip Error", e);
        }
    }

    private void _dirChecker(String dir) {
        File f = new File(dir);
        Log.i(TAG, "creating dir " + dir);

        if(dir.length() >= 0 && !f.isDirectory() ) {
            f.mkdirs();
        }
    }
}