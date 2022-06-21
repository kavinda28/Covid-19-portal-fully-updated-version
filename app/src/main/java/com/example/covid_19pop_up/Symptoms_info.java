package com.example.covid_19pop_up;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;

public class Symptoms_info extends AppCompatActivity {
    LottieAnimationView lottieAnimationView,lottieAnimationView2,lottieAnimationView3,lottieAnimationView4,lottieAnimationView5,lottieAnimationView6,lottieAnimationView7,lottieAnimationView8,lottieAnimationView9,lottieAnimationView10,lottieAnimationView11,lottieAnimationView12,lottieAnimationView13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_info);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsing_toolbar=findViewById(R.id.collapsing_toolbar);
        collapsing_toolbar.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.ExpandedAppBar);

        lottieAnimationView=findViewById(R.id.lottie);
        lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView2=findViewById(R.id.lottie2);
        lottieAnimationView2.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView3=findViewById(R.id.lottie3);
        lottieAnimationView3.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView4=findViewById(R.id.lottie4);
        lottieAnimationView4.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView5=findViewById(R.id.lottie5);
        lottieAnimationView5.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView6=findViewById(R.id.lottie6);
        lottieAnimationView6.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView7=findViewById(R.id.lottie7);
        lottieAnimationView7.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView8=findViewById(R.id.lottie8);
        lottieAnimationView8.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView9=findViewById(R.id.lottie9);
        lottieAnimationView9.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView10=findViewById(R.id.lottie10);
        lottieAnimationView10.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView11=findViewById(R.id.lottie11);
        lottieAnimationView11.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView12=findViewById(R.id.lottie12);
        lottieAnimationView12.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView13=findViewById(R.id.lottie13);
        lottieAnimationView13.setRepeatCount(LottieDrawable.INFINITE);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //Title bar back press triggers onBackPressed()
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
