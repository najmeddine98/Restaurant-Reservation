package com.najmeddine.BestReservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Timer;
import java.util.TimerTask;

public class Introdoctor extends AppCompatActivity {
    ImageView logo,splashImg;
    LottieAnimationView lottieAnimationView;
    TextView appName;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introdoctor);
        logo=findViewById(R.id.logo);
        splashImg=findViewById(R.id.image_bg);
        lottieAnimationView=findViewById(R.id.lottie);
        appName=findViewById(R.id.appName);
        timer= new Timer();
        splashImg.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent1 = new Intent(Introdoctor.this,NewLogin.class);
                startActivity(intent1);
            }
        },5800);

    }
}