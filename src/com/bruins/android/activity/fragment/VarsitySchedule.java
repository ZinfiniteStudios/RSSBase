package com.bruins.android.activity.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bruins.android.v2.R;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import roboguice.inject.InjectView;

/**
 * Created by david.hodge on 9/24/13.
 */
public class VarsitySchedule extends RoboSherlockFragment {

    @InjectView(R.id.game_1) RelativeLayout game1;
    @InjectView(R.id.game_2) RelativeLayout game2;
    @InjectView(R.id.game_3) RelativeLayout game3;
    @InjectView(R.id.game_4) RelativeLayout game4;
    @InjectView(R.id.game_5) RelativeLayout game5;
    @InjectView(R.id.game_6) RelativeLayout game6;
    @InjectView(R.id.game_7) RelativeLayout game7;
    Context mContext;
    private FadingActionBarHelper mFadingHelper;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        view = mFadingHelper.createView(inflater);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mFadingHelper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.abs__ab_bottom_solid_dark_holo)
                .headerLayout(R.layout.schedule_header)
                .parallax(true)
                .contentLayout(R.layout.fragment_schedule);
        mFadingHelper.initActionBar(getSherlockActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getBaseContext();

        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?f=d&daddr=36.8492,-76.405581"));
                intent.setComponent(new ComponentName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"));
                startActivity(intent);
            }
        });

        game2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?f=d&daddr=36.8492,-76.405581"));
                intent.setComponent(new ComponentName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"));
                startActivity(intent);
            }
        });

        game3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?f=d&daddr=36.688037,-76.242546"));
                intent.setComponent(new ComponentName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"));
                startActivity(intent);
            }
        });

        game4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?f=d&daddr=36.796021,-76.24013"));
                intent.setComponent(new ComponentName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"));
                startActivity(intent);
            }
        });

        game5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?f=d&daddr=36.720466,-76.662031"));
                intent.setComponent(new ComponentName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"));
                startActivity(intent);
            }
        });

        game6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?f=d&daddr=36.8492,-76.405581"));
                intent.setComponent(new ComponentName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"));
                startActivity(intent);
            }
        });

        game7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?f=d&daddr=36.8492,-76.405581"));
                intent.setComponent(new ComponentName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"));
                startActivity(intent);
            }
        });
    }

    public void locationNotAvail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
        builder.setMessage("Location information will be available soon, check back later.")
                .setTitle("Location coming soon!")
                .setPositiveButton("Okay, Thanks!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create();
        builder.show();
    }
}
