package com.example.covid_19pop_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import com.github.mikephil.charting.charts.PieChart;
import com.leo.simplearcloader.SimpleArcLoader;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Country_Details_show extends AppCompatActivity {
private int positionCountry;
TextView tvCountry,tvCase,tvRecovered,tvCritical,tvActive,tvTodayCase,tvTotaldeath,tvTodayDeath,Last_update,vaccine_text,vaccine_update;
PieChart pieChart2;
SwipeRefreshLayout refreshLayout;
    SimpleArcLoader simpleArcLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_country__details_show);
        refreshLayout=findViewById(R.id.refresh);//refresh activity
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(new Intent(getApplicationContext(),Country_Details_show.class));
                refreshLayout.setRefreshing(false);
                finish();
            }
        });
        Intent intent=getIntent();
        positionCountry=intent.getIntExtra("position",0);
        Log.i("positionCountry", String.valueOf(positionCountry));

        getSupportActionBar().setTitle("Details of "+AffectedCountries.countrtModelList.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        tvCountry=findViewById(R.id.tvCountry);
        tvCase=findViewById(R.id.tvCases);
        tvRecovered=findViewById(R.id.tvRecovered);
        tvCritical=findViewById(R.id.tvCritical);
        tvActive=findViewById(R.id.tvActive);
        tvTodayCase=findViewById(R.id.tvTodayCases);
        tvTotaldeath=findViewById(R.id.tvDeaths);
        tvTodayDeath=findViewById(R.id.tvTodayDeaths);

        vaccine_text=findViewById(R.id.vaccine);
        vaccine_update=findViewById(R.id.last_vaccine_update);

        pieChart2=findViewById(R.id.piechart2);
        Last_update=findViewById(R.id.last_update);
        simpleArcLoader=findViewById(R.id.loader);
        simpleArcLoader.start();

        tvCountry.setText(AffectedCountries.countrtModelList.get(positionCountry).getCountry());
        tvCase.setText(AffectedCountries.countrtModelList.get(positionCountry).getCases());
        tvRecovered.setText(AffectedCountries.countrtModelList.get(positionCountry).getRecovered());
        tvCritical.setText(AffectedCountries.countrtModelList.get(positionCountry).getCritical());
        tvActive.setText(AffectedCountries.countrtModelList.get(positionCountry).getActive());
        tvTodayCase.setText(AffectedCountries.countrtModelList.get(positionCountry).getTodayCase());
        tvTotaldeath.setText(AffectedCountries.countrtModelList.get(positionCountry).getDeaths());
        tvTodayDeath.setText(AffectedCountries.countrtModelList.get(positionCountry).getTodayDeaths());
        System.out.println("update value:::"+AffectedCountries.countrtModelList.get(positionCountry).getUpdated());

        String Dates= updatedate(AffectedCountries.countrtModelList.get(positionCountry).getUpdated());
        System.out.println("specific"+AffectedCountries.countrtModelList.get(positionCountry).getUpdated());
        String Time=millsToDateFormat(Long.parseLong(AffectedCountries.countrtModelList.get(positionCountry).getUpdated()));
        Last_update.setText("Here's Info as at "+Time+" , "+Dates+" , Pull to Refresh");


        String countryname=AffectedCountries.countrtModelList.get(positionCountry).getCountry();
        fetchData2(countryname);//vaccination

        //pupulating list of PieEntires
        List<PieEntry> pieEntires = new ArrayList<>();

        pieEntires.add(new PieEntry(Integer.parseInt(tvCase.getText().toString()),0));
        pieEntires.add(new PieEntry(Integer.parseInt(tvRecovered.getText().toString()),1));
        pieEntires.add(new PieEntry(Integer.parseInt(tvActive.getText().toString()),4));
        pieEntires.add(new PieEntry(Integer.parseInt(tvTotaldeath.getText().toString()),3));


        PieDataSet dataSet = new PieDataSet(pieEntires,"");
        dataSet.setColors(getResources().getColor(R.color.yellow), getResources().getColor(R.color.purple), getResources().getColor(R.color.seeblue), getResources().getColor(R.color.red));
        PieData data = new PieData(dataSet);
        //Get the chart
        pieChart2.setData(data);
        pieChart2.invalidate();
        pieChart2.setCenterText("All Time");
        pieChart2.setCenterTextSize(17f);
        pieChart2.setCenterTextColor(Color.GRAY);
        dataSet.setDrawValues(false);
        pieChart2.setHoleRadius(65);
        pieChart2.setDrawRoundedSlices(true);
        pieChart2.getDescription().setEnabled(false);
        //animation
        // pieChart.spin( 2000,0,-360f, Easing.EaseInOutQuad);
        //  pieChart.animateXY(3000, 3000);
        pieChart2.animateY(3000, Easing.EaseInBack);

        //legend attributes
        Legend legend = pieChart2.getLegend();
        legend.setEnabled(false);
//        pieChart2.addPieSlice(new PieModel("cases",Integer.parseInt(tvCase.getText().toString()), Color.parseColor("#FFA726")));
//        pieChart2.addPieSlice(new PieModel("recovered",Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
//        pieChart2.addPieSlice(new PieModel("deaths",Integer.parseInt(tvTotaldeath.getText().toString()), Color.parseColor("#EF5350")));
//        pieChart2.addPieSlice(new PieModel("active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
//        pieChart2.startAnimation();

        simpleArcLoader.stop();
        simpleArcLoader.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    private String updatedate(String updated) {
        DateFormat format=new SimpleDateFormat("MMM dd, yyyy");
        long milliseconds=Long.parseLong(updated);

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        System.out.println(format.format(calendar.getTime()));
        return format.format(calendar.getTime());

    }

    public String millsToDateFormat(long mills) {

        Date date = new Date(mills);
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateFormatted = formatter.format(date);
        System.out.println(dateFormatted);
        return dateFormatted;
    }


    private void fetchData2(String country_name) {

        String url="https://disease.sh/v3/covid-19/vaccine/coverage/countries/"+country_name+"?lastdays=1";
        System.out.println(url);


        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject=new JSONObject(response.toString());
                    JSONObject object=jsonObject.getJSONObject("timeline");
                    Iterator<String> keys= object.keys();
                    while (keys.hasNext())
                    {
                        String keyValue = (String)keys.next();
                        String valueString = object.getString(keyValue);
                        vaccine_text.setText(valueString);
                        vaccine_update.setText("Last Update "+keyValue);
                        System.out.println("vaccienet::::::::::"+keyValue);
                    }





                } catch (JSONException e) {
                    e.printStackTrace();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Country_Details_show.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(Country_Details_show.this);
        requestQueue.add(request);
    } //update vaccine date
}
