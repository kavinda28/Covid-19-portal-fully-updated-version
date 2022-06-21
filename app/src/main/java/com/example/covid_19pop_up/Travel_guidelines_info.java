package com.example.covid_19pop_up;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;

public class Travel_guidelines_info extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    TextView myTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_guidelines_info);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsing_toolbar=findViewById(R.id.collapsing_toolbar);
        collapsing_toolbar.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.ExpandedAppBar);



        lottieAnimationView=findViewById(R.id.lottie);
        lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
        myTextView=findViewById(R.id.textViewph);
      //  myTextView.setText(Html.fromHtml("<p>Wherever you are, follow COVID-19 <br>prevention measures,such as <br>wearing a mask, washing hands <br>frequently,maintaining <br>physical distance.</p>"));
    }
    public boolean onOptionsItemSelected(MenuItem item) { //back_button
        if (item.getItemId() == android.R.id.home) {
            //Title bar back press triggers onBackPressed()
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
