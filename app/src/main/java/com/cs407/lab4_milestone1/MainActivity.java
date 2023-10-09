package com.cs407.lab4_milestone1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button startButton;
    private Button stopButton;

    private volatile boolean stopThread = false;
    private TextView percentageGiven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        percentageGiven = findViewById(R.id.downloadProgress);

    }

    public void mockFileDownloader() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("DownLoading. ..");
            }
        });

        //startButton.setText ("DOWNLOADING...");

        for (int downloadProgress = 0; downloadProgress <= 100; downloadProgress = downloadProgress + 10) {
            int downloadProgess;
            final int percentage = downloadProgress;
            if (stopThread) {
                runOnUiThread (new Runnable() {
                    @Override
                    public void run() {
                        startButton.setText ("Start") ;
                    }
                });
                return;
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    percentageGiven.setText("Download Progress: " + percentage + "%");
                }
            });
            Log.d(TAG, "DownLoad Progress: " + downloadProgress + "%");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Start");
            }
        });
        
    }

    public void startDownload(View view){
        //mockFileDownloader ( ) ;
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable () ;
        new Thread (runnable).start();
    }

    public void stopDownload (View view) {
        stopThread = true;
    }

    class ExampleRunnable implements Runnable {
        @Override
        public void run() {
            mockFileDownloader();
        }
    }

}



















