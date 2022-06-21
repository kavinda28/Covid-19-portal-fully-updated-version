package com.example.covid_19pop_up;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.leo.simplearcloader.SimpleArcLoader;

import com.github.mikephil.charting.charts.PieChart;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link specific_country_covid_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class specific_country_covid_details extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tvCountry,tvCase,tvRecovered,tvCritical,tvActive,tvTodayCase,tvTotaldeath,tvTodayDeath,Last_update,vaccine_text,vaccine_update,country_name_text;
    PieChart pieChart2;
    SwipeRefreshLayout refreshLayout;
    SimpleArcLoader simpleArcLoader;
    public static List<CountrtModel> countrtModelList=new ArrayList<>();
    specific_country_covid_details(List<CountrtModel> countrtModelsList){

        this.countrtModelList=countrtModelsList;

    }



    public specific_country_covid_details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment specific_country_covid_details.
     */
    // TODO: Rename and change types and number of parameters
    public static specific_country_covid_details newInstance(String param1, String param2) {
        specific_country_covid_details fragment = new specific_country_covid_details();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_specific_country_covid_details, container, false);


        refreshLayout=view.findViewById(R.id.refresh);//refresh activity
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(new Intent(getContext(), Home.class));
                refreshLayout.setRefreshing(false);
                getActivity().finish();
            }
        });
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        String Country_name=sharedPreferences.getString("Country_name","sri");
        System.out.println("sharedPreferences::::::"+Country_name);

//        getSupportActionBar().setTitle("Details of "+Country_name);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvCountry=view.findViewById(R.id.tvCountry);
        tvCase=view.findViewById(R.id.tvCases);
        tvRecovered=view.findViewById(R.id.tvRecovered);
        tvCritical=view.findViewById(R.id.tvCritical);
        tvActive=view.findViewById(R.id.tvActive);
        tvTodayCase=view.findViewById(R.id.tvTodayCases);
        tvTotaldeath=view.findViewById(R.id.tvDeaths);
        tvTodayDeath=view.findViewById(R.id.tvTodayDeaths);
        simpleArcLoader=view.findViewById(R.id.loader);
        pieChart2=view.findViewById(R.id.piechart2);

        vaccine_text=view.findViewById(R.id.vaccine);
        vaccine_update=view.findViewById(R.id.last_vaccine_update);
        country_name_text=view.findViewById(R.id.show_country_name);
        country_name_text.setText(Country_name+" Stats");
        Last_update=view.findViewById(R.id.last_update);
        Country_name=Country_name.replaceAll(" ", "%20");
        System.out.println("ooooooooooooooooo"+Country_name);
        fetchData(Country_name);
        fetchData2(Country_name);
        return view;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            getActivity().finish();
        return super.onOptionsItemSelected(item);
    }

    private void fetchData(String countryname) {
//        country_name_text.setText(countryname+" Stats");
        String url="https://disease.sh/v3/covid-19/countries/"+countryname;
        System.out.println(url);
        simpleArcLoader.start();

        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject=new JSONObject(response.toString());
                    tvCountry.setText(jsonObject.getString("country"));
                    tvCase.setText(jsonObject.getString("cases"));
                    tvRecovered.setText(jsonObject.getString("recovered"));
                    tvCritical.setText(jsonObject.getString("critical"));
                    tvActive.setText(jsonObject.getString("active"));
                    tvTodayCase.setText(jsonObject.getString("todayCases"));
                    tvTotaldeath.setText(jsonObject.getString("deaths"));
                    tvTodayDeath.setText(jsonObject.getString("todayDeaths"));


                    String Dates= updatedate(jsonObject.getString("updated"));
                    System.out.println("specific"+jsonObject.getString("updated"));
                    String Time=millsToDateFormat(Long.parseLong(jsonObject.getString("updated")));
                    Last_update.setText("Here's Info as at "+Time+" , "+Dates+" , Pull to Refresh");


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

//                    pieChart2.addPieSlice(new PieModel("cases",Integer.parseInt(tvCase.getText().toString()), Color.parseColor("#FFA726")));
//                    pieChart2.addPieSlice(new PieModel("recovered",Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
//                    pieChart2.addPieSlice(new PieModel("deaths",Integer.parseInt(tvTotaldeath.getText().toString()), Color.parseColor("#EF5350")));
//                    pieChart2.addPieSlice(new PieModel("active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
//                    pieChart2.startAnimation();

                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);


                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);
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

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    } //update vaccine date






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
}
