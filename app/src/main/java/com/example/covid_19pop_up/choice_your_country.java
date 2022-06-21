package com.example.covid_19pop_up;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class choice_your_country extends AppCompatActivity {
 EditText edtSearch;
 ListView listView;
 SimpleArcLoader simpleArcLoader;
 public static List<CountrtModel> countrtModelList=new ArrayList<>();
 CountrtModel countrtModel;
 customerAdapter customerAdapter;
    String select_country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);

        getSupportActionBar().setTitle("Choice yor Country");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        edtSearch=findViewById(R.id.edtSearch);
        listView=findViewById(R.id.listView);
        simpleArcLoader=findViewById(R.id.loader);

        SharedPreferences sharedPreferences=this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
         select_country=sharedPreferences.getString("select_country","10");
        System.out.println("select_country::::::::::::"+select_country);
        if(select_country.equals("1")){
           startActivity(new Intent(getApplicationContext(),Home.class));
           finish();
        }

        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String country_name= countrtModelList.get(position).getCountry();
                String country_flag= countrtModelList.get(position).getFlag();
                System.out.println("country_name:::::::::::"+country_name);
                System.out.println("country_flag:::::::::::"+country_flag);
                save_country_name(country_name,country_flag);

               startActivity(new Intent(getApplicationContext(),Home.class));
               finish();

            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 customerAdapter.getFilter().filter(s);
                 customerAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    private void fetchData() {
        String url="https://disease.sh/v3/covid-19/countries";
//old https://corona.lmao.ninja/v2/countries
        simpleArcLoader.start();

        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray  jsonArray=new JSONArray(response);
                    for (int i=0; i < jsonArray.length(); i++){

                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        String CountryName= jsonObject.getString("country");
                        String cases= jsonObject.getString("cases");
                        String todayCases= jsonObject.getString("todayCases");
                        String deaths= jsonObject.getString("deaths");
                        String todayDeaths= jsonObject.getString("todayDeaths");
                        String recovered= jsonObject.getString("recovered");
                        String active= jsonObject.getString("active");
                        String critical= jsonObject.getString("critical");
                        String updated= jsonObject.getString("updated");

            JSONObject object=jsonObject.getJSONObject("countryInfo");
            String flagUrl=object.getString("flag");

            countrtModel = new CountrtModel(flagUrl,CountryName,cases,todayCases,deaths,todayDeaths,recovered,active,critical,updated);
            countrtModelList.add(countrtModel);
                    }

                    customerAdapter =new customerAdapter(choice_your_country.this,countrtModelList);//parse affected country list details to adpter class
                    listView.setAdapter(customerAdapter);//loard list view get data from adpter class
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

                Toast.makeText(choice_your_country.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    public void save_country_name(String name,String flag) {
        SharedPreferences sharedPreferences=this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);

        sharedPreferences.edit().putString("Country_name",name).apply();
        sharedPreferences.edit().putString("Country_flag",flag).apply();
        sharedPreferences.edit().putString("select_country", String.valueOf(1)).apply();
        select_country="1";

    }
}
