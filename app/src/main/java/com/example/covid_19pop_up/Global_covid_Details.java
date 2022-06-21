package com.example.covid_19pop_up;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
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

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
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
 * Use the {@link Global_covid_Details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Global_covid_Details extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView tvCases,tvRecovered,tvCritical,tvActive,tvTodayCase,tvTotalDeaths,tvTodayDeaths,tvAffectedCountries,Last_update,vaccine_text,vaccine_update;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;
    SwipeRefreshLayout refreshLayout;
    Button showcountry;
ViewPager viewPager;
    public static List<CountrtModel> countrtModelListarray=new ArrayList<>();
    CountrtModel countrtModel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Global_covid_Details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Global_covid_Details.
     */
    // TODO: Rename and change types and number of parameters
    public static Global_covid_Details newInstance(String param1, String param2) {
        Global_covid_Details fragment = new Global_covid_Details();
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
        final View view= inflater.inflate(R.layout.fragment_global_covid__details, container, false);

        if(!isConnected()){
            showCustomerDialog();
        }else {
            System.out.println("...............................done network");
        }
        viewPager=view.findViewById(R.id.view_pager);
        refreshLayout=view.findViewById(R.id.refresh);//refresh activity
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("refresh");
                startActivity(new Intent(getContext(), Home.class));
                refreshLayout.setRefreshing(false);

              getActivity().finish();
            }
        });

        tvCases=view.findViewById(R.id.tvCases);
        tvRecovered=view.findViewById(R.id.tvRecovered);
        tvCritical=view.findViewById(R.id.tvcritical);
        tvActive=view.findViewById(R.id.tvActive);
        tvTodayCase=view.findViewById(R.id.tvTodayCases);
        tvTotalDeaths=view.findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths=view.findViewById(R.id.tvTodayDeaths);
        tvAffectedCountries=view.findViewById(R.id.tvAffectedCountries);

        simpleArcLoader=view.findViewById(R.id.loader);
        scrollView=view.findViewById(R.id.scrollStats);
        pieChart=view.findViewById(R.id.chart);
        Last_update=view.findViewById(R.id.last_update);


        vaccine_text=view.findViewById(R.id.vaccine);
        vaccine_update=view.findViewById(R.id.last_vaccine_update);

        fetchData();// fetch global covid updates
        fetchData2();// fetch globle vaccine data

        return view;


    }



    private void fetchData() {
        String url="https://disease.sh/v3/covid-19/all";

        simpleArcLoader.start();

        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject=new JSONObject(response.toString());
                    tvCases.setText(jsonObject.getString("cases"));
                    tvRecovered.setText(jsonObject.getString("recovered"));
                    tvCritical.setText(jsonObject.getString("critical"));
                    tvActive.setText(jsonObject.getString("active"));
                    tvTodayCase.setText(jsonObject.getString("todayCases"));
                    tvTotalDeaths.setText(jsonObject.getString("deaths"));
                    tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                    tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));

                    String Dates= updatedate(jsonObject.getString("updated"));
                    System.out.println("globle"+jsonObject.getString("updated"));
                    String Time=millsToDateFormat(Long.parseLong(jsonObject.getString("updated")));
                    Last_update.setText("Here's Info as at "+Time+" , "+Dates+" , Pull to Refresh");
                    //pupulating list of PieEntires
                    List<PieEntry> pieEntires = new ArrayList<>();

                    pieEntires.add(new PieEntry(Integer.parseInt(tvCases.getText().toString()),0));
                    pieEntires.add(new PieEntry(Integer.parseInt(tvRecovered.getText().toString()),1));
                    pieEntires.add(new PieEntry(Integer.parseInt(tvActive.getText().toString()),4));
                    pieEntires.add(new PieEntry(Integer.parseInt(tvTotalDeaths.getText().toString()),3));
                    PieDataSet dataSet = new PieDataSet(pieEntires,"");
                    dataSet.setColors(getResources().getColor(R.color.yellow), getResources().getColor(R.color.purple), getResources().getColor(R.color.seeblue), getResources().getColor(R.color.red));
                    PieData data = new PieData(dataSet);
                    //Get the chart
                    pieChart.setData(data);
                    pieChart.invalidate();
                    pieChart.setCenterText("All Time");
                    pieChart.setCenterTextSize(17f);
                    pieChart.setCenterTextColor(Color.GRAY);
                    dataSet.setDrawValues(false);
                    pieChart.setHoleRadius(65);
                    pieChart.setDrawRoundedSlices(true);
                    pieChart.getDescription().setEnabled(false);
                    //animation
                    // pieChart.spin( 2000,0,-360f, Easing.EaseInOutQuad);
                    //  pieChart.animateXY(3000, 3000);
                    pieChart.animateY(3000, Easing.EaseInBack);

                    //legend attributes
                    Legend legend = pieChart.getLegend();
                    legend.setEnabled(false);



//                    pieChart.addPieSlice(new PieModel("cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
//                    pieChart.addPieSlice(new PieModel("recovered",Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
//                    pieChart.addPieSlice(new PieModel("deaths",Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
//                    pieChart.addPieSlice(new PieModel("active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
//                    pieChart.startAnimation();

                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }


    private void fetchData2() {

        String url="https://disease.sh/v3/covid-19/vaccine/coverage?lastdays=1&fullData=true";
        System.out.println(url);


        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jsonarray=new JSONArray(response.toString());
                    JSONObject jsonobject = jsonarray.getJSONObject(0);
                    System.out.println("globle vaccine::::::::::::::::::::::"+jsonobject.getString("total"));
                    System.out.println("globle vaccine daily::::::::::::::::::::::"+jsonobject.getString("daily"));
                    System.out.println("globle vaccine date::::::::::::::::::::::"+jsonobject.getString("date"));
                    vaccine_text.setText(jsonobject.getString("total"));
                      vaccine_update.setText("Last Update "+jsonobject.getString("date"));






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
    private void showCustomerDialog() {
        System.out.println("...............................showCustomerDialog");


        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setMessage("Please Connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // startActivity(new Intent(getApplicationContext(),AffectedCountries.class));
                        // finish();
                    }
                });
        builder.show();

    } //show network ont working popup

    private boolean isConnected() {
        ConnectivityManager connectivityManager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())){
            return  true;
        }else {
            return false;
        }


    }

}
