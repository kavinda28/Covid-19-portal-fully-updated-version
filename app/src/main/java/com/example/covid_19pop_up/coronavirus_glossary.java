package com.example.covid_19pop_up;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Objects;

public class coronavirus_glossary extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
TextView myTextView;

    RecyclerView recyclerView;
    ArrayList<info_class> info_classArrayList;
    mtadapter myadapter;
    String []info_heading;
    String []info_Text;

    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coronavirus_glossary);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsing_toolbar=findViewById(R.id.collapsing_toolbar);
        collapsing_toolbar.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.ExpandedAppBar);



        lottieAnimationView=findViewById(R.id.lottie);
        lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
        myTextView=findViewById(R.id.textViewph);
        //myTextView.setText(Html.fromHtml("<p>Get definitions of unfamiliar <br> words you may be hearing <br> during the coronavirus pandemic."));


        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        info_classArrayList = new ArrayList<info_class>();


        info_heading = new String[]{
                "Isolate",
                "Social distance",
                "An outbreak",
                "an epidemic",
                "a pandemic",
                "self-quarantine",
                "Contagious",
                "Infectious",
                "containing",
                "Anosmia",
                "Bacteria",
                "Hygiene",
                "Intubation",
                "Cluster",
                "Flattening the curve",

                "Self-monitoring",
                "Quarantine",
                "PPE",
                "Vaccine",
                "Ventilator",
                "Incubation period",
                "Symptomatic",
                "Critical worker",
                "Lockdown",
                "Contagious",
                "Face visor/shield",
                "Vector/s",
                "Screening",
                "Super-spreader",
                "Contactless",
                "Clinical trial"
        };

        info_Text = new String[]{
                "to set apart from others",
                "the avoidance of close contact with other people during the outbreak of a contagious disease in order to minimize exposure and reduce the transmission of infection.",
                "a sudden rise in the incidence of a disease.",
                "an outbreak of disease that spreads quickly and affects many individuals at the same time.",
                "an outbreak of a disease that occurs over a wide geographic area and affects an exceptionally high proportion of the population.",
                "to refrain from any contact with other individuals for a period of time.",
                "transmissible by direct or indirect contact with an infected person.",
                "producing or capable of producing the infection.",
                "pathogenic agents which may be transmitted.",
                "the loss of the sense of smell, either total or partial.",
                "Microscopic, single-celled organisms that can cause human diseases.",
                "Actions that prevent the spread of disease and maintain health through cleanliness.",
                "This is a medical procedure that may be used for people with severe COVID-19 who are unable to breathe on their own.",
                "A collection of cases occurring in the same place at the same time",
                "Slowing the spread of the virus",

                "This simply means checking yourself for COVID-19 symptoms.",
                "It involves separating and restricting the movements of people who were exposed to a contagious disease to see if they become sick.",
                "(Personal protective equipment) Specialized clothing or equipment, worn by an employee for protection against infectious materials.",
                "It triggers the immune system to help it build immunity to a disease.",
                "This is a machine to help patients breathe when their lungs are damaged, and they can’t get enough oxygen on their own.",
                "it is the time period between contracting a virus and emergence of its symptoms.",
                "Showing symptoms of disease.",
                "A critical worker is someone who is doing a job that must carry on during the Coronavirus crisis.",
                "It means that we should all stay at home and keep away from other people.",
                "This means that a disease can be spread from one person to another, typically by direct contact.",
                "A face visor or shield is made out of plastic and covers the entire face",
                " In medicine, a vector is a carrier of disease.",
                "the act of verifying symptoms and potential exposure before testing for the virus.",
                "a highly contagious individual who can spread an infectious disease to a large number of uninfected people through a network of contacts.",
                "without contact; for example, “contactless delivery” would include leaving purchased items at the entryway of a home rather than handing it directly to a person.",
                "research experiments on human participants designed to answer questions about new treatments; in the case of COVID-19 and coronaviruses, the safety and efficacy of a potential vaccine."
        };

        getDate();
    }

    public boolean onOptionsItemSelected(MenuItem item) { //back_button
        if (item.getItemId() == android.R.id.home) {
            //Title bar back press triggers onBackPressed()
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getDate() {
        for (int i = 0; i<info_heading.length;i++){

            info_class info_class=new info_class(info_heading[i],info_Text[i]);
            info_classArrayList.add(info_class);
        }
        myadapter = new mtadapter(this,info_classArrayList);
        recyclerView.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem menuItem = menu.findItem(R.id.search_action);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search Hear!");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                myadapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
