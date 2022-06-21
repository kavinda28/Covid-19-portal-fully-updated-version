package com.example.covid_19pop_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class splash_screen extends AppCompatActivity {
    LottieAnimationView lottieAnimationView,lottieAnimationView2;
    Button lestgo;
    String select_country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //full screen window open

        SharedPreferences sharedPreferences=this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        select_country=sharedPreferences.getString("select_country","10");
        System.out.println("select_country::::::::::::"+select_country);
        if(select_country.equals("1")){
            startActivity(new Intent(getApplicationContext(),Home.class));
            finish();
        }
        lottieAnimationView=findViewById(R.id.lottie);
        lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);

        lottieAnimationView2=findViewById(R.id.lottie2);
        lottieAnimationView2.setRepeatCount(LottieDrawable.INFINITE);

        lestgo = findViewById(R.id.gobtn);
        lestgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(splash_screen.this, choice_your_country.class);
                startActivity(in);
                Animatoo.animateShrink(splash_screen.this);
             finish();
            }
        });

    }

}
