package com.example.covid_19pop_up;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link menu_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class menu_details extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
CardView symptoms_card,face_mask_card,travel_card,QR_card,glossary_card;
    LottieAnimationView lottieAnimationView,lottieAnimationView2,lottieAnimationView3,lottieAnimationView4,lottieAnimationView5,lottieAnimationView6,lottieAnimationView7,lottieAnimationView8,lottieAnimationView9,lottieAnimationView10,lottieAnimationView11;
    public menu_details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment menu_details.
     */
    // TODO: Rename and change types and number of parameters
    public static menu_details newInstance(String param1, String param2) {
        menu_details fragment = new menu_details();
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
        final View view = inflater.inflate(R.layout.fragment_menu_details, container, false);


        lottieAnimationView=view.findViewById(R.id.lottie);
        lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);

        lottieAnimationView2=view.findViewById(R.id.lottie2);
        lottieAnimationView2.setRepeatCount(LottieDrawable.INFINITE);

        lottieAnimationView3=view.findViewById(R.id.lottie3);
        lottieAnimationView3.setRepeatCount(LottieDrawable.INFINITE);

        lottieAnimationView4=view.findViewById(R.id.lottie4);
        lottieAnimationView4.setRepeatCount(LottieDrawable.INFINITE);

        lottieAnimationView5=view.findViewById(R.id.lottie5);
        lottieAnimationView5.setRepeatCount(LottieDrawable.INFINITE);

        lottieAnimationView6=view.findViewById(R.id.lottie6);
        lottieAnimationView6.setRepeatCount(LottieDrawable.INFINITE);


        lottieAnimationView7=view.findViewById(R.id.lottie7);
        lottieAnimationView7.setRepeatCount(LottieDrawable.INFINITE);


        lottieAnimationView8=view.findViewById(R.id.lottie8);
        lottieAnimationView8.setRepeatCount(LottieDrawable.INFINITE);

        lottieAnimationView9=view.findViewById(R.id.lottie9);
        lottieAnimationView9.setRepeatCount(LottieDrawable.INFINITE);

        lottieAnimationView10=view.findViewById(R.id.lottie10);
        lottieAnimationView10.setRepeatCount(LottieDrawable.INFINITE);

        lottieAnimationView11=view.findViewById(R.id.lottie11);
        lottieAnimationView11.setRepeatCount(LottieDrawable.INFINITE);

        symptoms_card=view.findViewById(R.id.symptoms_card);
        symptoms_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getContext(), Symptoms_info.class);

            startActivity(intent);
        }
    });

        face_mask_card=view.findViewById(R.id.face_mask_info);
        face_mask_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), face_masks_info.class);

                startActivity(intent);
            }
        });

        travel_card=view.findViewById(R.id.travel_card);
        travel_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Travel_guidelines_info.class);

                startActivity(intent);
            }
        });

        QR_card=view.findViewById(R.id.QR_card);
        QR_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), QR_code_scanner.class);

                startActivity(intent);
            }
        });
        glossary_card=view.findViewById(R.id.CoronavirusGlossary_card);
        glossary_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), coronavirus_glossary.class);

                startActivity(intent);
            }
        });
        return view;
    }
}
