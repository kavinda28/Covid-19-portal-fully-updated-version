package com.example.covid_19pop_up;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Home extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    String Country_flag;

    private String version;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,databaseReference2;
    private String appUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_home);

//        ActionBar actionBar = getSupportActionBar();
////        actionBar.hide();
        tabLayout=findViewById(R.id.tablayout_id);
        viewPager=findViewById(R.id.view_pager);

        ArrayList<String> arrayList=new ArrayList<>();
CheckForupdate();
        arrayList.add("Global");
        SharedPreferences sharedPreferences=this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        String Country_name=sharedPreferences.getString("Country_name","sri");
        arrayList.add(""+Country_name);
        arrayList.add("Info");

        Country_flag=sharedPreferences.getString("Country_flag","");
        System.out.println("home Country_flag:::"+Country_flag);
        prepareViewpager(viewPager,arrayList);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_earthglobe);
    //    tabLayout.getTabAt(1).setCustomView(Integer.parseInt(Country_flag));

       tabLayout.getTabAt(1).setCustomView(createTabItemView(Country_flag));
       // fetchData2();
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_infosvg);

    }

    private void CheckForupdate() {

        try {
            version = this.getPackageManager().getPackageInfo(getPackageName(),0).versionName;

            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("Version").child("versionNumber");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String versionName = (String) snapshot.getValue();

                    if (!versionName.equals(version)){
                        AlertDialog alertDialog = new AlertDialog.Builder(Home.this)
                                .setTitle("New Version Available!")
                                .setMessage("Plese update our app to the latest version for continuous use.")
                                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Version").child("appUrl");
                                        myRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                appUrl=snapshot.getValue().toString();
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl)));
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        System.exit(0);
                                    }
                                })
                                .create();
                        alertDialog.setCancelable(false);
                        alertDialog.setCanceledOnTouchOutside(false);

                        alertDialog.show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {

        }
    }


    private void prepareViewpager(ViewPager viewPager, ArrayList<String> arrayList) {
        MainAdapter adpter=new MainAdapter(getSupportFragmentManager());

        Global_covid_Details  global_covid_details=new Global_covid_Details();
        specific_country_covid_details  specificCountryCovidDetails=new specific_country_covid_details();
menu_details menuDetails=new menu_details();

        Bundle bundle=new Bundle();
        global_covid_details.setArguments(bundle);
        specificCountryCovidDetails.setArguments(bundle);
        menuDetails.setArguments(bundle);
        adpter.addFragment(global_covid_details,arrayList.get(0));
        adpter.addFragment(specificCountryCovidDetails,arrayList.get(1));
        adpter.addFragment(menuDetails,arrayList.get(2));

        global_covid_details=new Global_covid_Details();
        specificCountryCovidDetails=new specific_country_covid_details();
        menuDetails=new menu_details();

        viewPager.setAdapter(adpter);
    }

    private class MainAdapter extends FragmentPagerAdapter {
        ArrayList<String> arrayList=new ArrayList<>();
        List<Fragment> fragmentList=new ArrayList<>();
int[] imageList= {R.drawable.ic_earthglobe, R.drawable.ic_earthglobe};
        public void addFragment(Fragment fragment,String title){
            arrayList.add("   "+title);
            fragmentList.add(fragment);

        }

        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
//            Drawable drawable= ContextCompat.getDrawable(getApplicationContext(),imageList[position]);
//            drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
//
//            SpannableString spannableString=new SpannableString(""+
//                    arrayList.get(position));
//
//            ImageSpan imageSpan=new ImageSpan(drawable,ImageSpan.ALIGN_BOTTOM);
//
//            spannableString.setSpan(imageSpan,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//
            return arrayList.get(position);
        }
    }

    public void GoTrack_Countries(View view) {

        startActivity(new Intent(this,AffectedCountries.class));

    }  //AffectedCountries call this method run by 'fragment-global covid'

    private View createTabItemView(String imgUri) {
        ImageView imageView = new ImageView(this);
        TabLayout.LayoutParams params = new TabLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        Picasso.get().load(imgUri).resize(140, 90).into(imageView);
        return imageView;
    }




//    private void fetchData2() {
//
//        String url="https://disease.sh/v3/covid-19/vaccine/coverage/countries/Sri%20Lanka?lastdays=1";
//        System.out.println(url);
//
//
//        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//
//                    JSONObject jsonObject=new JSONObject(response.toString());
////                    tvCountry.setText(jsonObject.getString("country"));
//                    JSONObject object=jsonObject.getJSONObject("timeline");
//                    Iterator<String> keys= object.keys();
//                    while (keys.hasNext())
//                    {
//                        String keyValue = (String)keys.next();
//                        String valueString = object.getString(keyValue);
//                        System.out.println("vaccienet::::::::::"+valueString);
//                    }
//
//
//
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        RequestQueue requestQueue= Volley.newRequestQueue(Home.this);
//        requestQueue.add(request);
//    }
}
