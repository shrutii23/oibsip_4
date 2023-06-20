package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button stopButton;
    private Button holdButton;
    private TextView timeTextView;

    private Handler handler;
    private Runnable runnable;
    private long startTime;
    private long elapsedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        holdButton = findViewById(R.id.holdButton);
        timeTextView = findViewById(R.id.timeTextView);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                elapsedTime = System.currentTimeMillis() - startTime;
                updateTimer(elapsedTime);
                handler.postDelayed(this, 1000);
            }
        };

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holdTimer();
            }
        });
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
        handler.postDelayed(runnable, 0);
    }

    private void stopTimer() {
        handler.removeCallbacks(runnable);
        updateTimer(0);
    }

    private void holdTimer() {
        handler.removeCallbacks(runnable);
    }

    private void updateTimer(long milliseconds) {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeTextView.setText(time);
    }
}
