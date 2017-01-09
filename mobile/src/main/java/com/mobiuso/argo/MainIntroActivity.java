package com.mobiuso.argo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.mobiuso.argo.bundle.DownloadManager;
import com.mobiuso.argo.bundle.ManifestHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ashutosh on 06/01/17.
 */

public class MainIntroActivity extends IntroActivity {

    private static final String TAG = "MainIntroActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){

        setFullscreen(true);

        super.onCreate(savedInstanceState);

        /* Add your own page change listeners */
        addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "OnPageScrolled : Position is " + position);
            }
            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected : Position is " + position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged : State is " + state);
            }
        });


        // Add Slides
        addSlide(new SimpleSlide.Builder()
                    .title("Introduction Slide 1")
                    .background(R.color.colorPrimary)
                    .build());

        addSlide(new SimpleSlide.Builder()
                    .title("Introduction Slide 2")
                    .background(R.color.colorPrimary)
                    .build());

        addSlide(new SimpleSlide.Builder()
                    .title("Google Sign In")
                    .background(R.color.colorPrimary)
                    .buttonCtaLabel("Test")
                    .buttonCtaClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainIntroActivity.this, testActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .build());
    }

    @Override
    public void onResume(){
        super.onResume();
//        ManifestHandler manifestHandler = new ManifestHandler(new ManifestHandler.PostDownload() {
//            @Override
//            public void downloadDone(ArrayList<HashMap<String, String>> list) {
//                DownloadManager downloadManager = new DownloadManager(MainIntroActivity.this, list);
//            }
//        });
//        manifestHandler.execute();
    }
}
