package com.bruins.android.activity.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.bruins.android.R;
import roboguice.inject.InjectView;
import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: david.hodge
 * Date: 9/22/13
 * Time: 7:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleFragment extends RoboSherlockFragment {

    @InjectView(R.id.game_1) RelativeLayout game1;
    @InjectView(R.id.game_2) RelativeLayout game2;
    @InjectView(R.id.game_3) RelativeLayout game3;
    @InjectView(R.id.game_4) RelativeLayout game4;
    @InjectView(R.id.game_5) RelativeLayout game5;
    @InjectView(R.id.game_6) RelativeLayout game6;
    @InjectView(R.id.game_7) RelativeLayout game7;
    Context mContext;
    private FadingActionBarHelper mFadingHelper;
    private Bundle mArguments;
    public static final String ARG_IMAGE_RES = "image_source";
    public static final String ARG_ACTION_BG_RES = "image_action_bs_res";

//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        FadingActionBarHelper helper = new FadingActionBarHelper()
//                .actionBarBackground(R.style.AppTheme)
//                .headerLayout(R.layout.hos_vpoint_header)
//                .parallax(true)
//                .contentLayout(R.layout.fragment_schedule);
//
//        setContentView(helper.createView(this));
//        helper.initActionBar(this);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = mFadingHelper.createView(inflater);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

//        mArguments = getArguments();
//        int actionBarBg = mArguments != null ? mArguments.getInt(ARG_ACTION_BG_RES) : R.style.ScheduleTheme;

//        getSherlockActivity().setTheme(R.style.ScheduleTheme);

        mFadingHelper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.ab_solid_bruins)
                .headerLayout(R.layout.schedule_header)
                .parallax(true)
                .lightActionBar(false)
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
                locationNotAvail();
            }
        });

        game5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationNotAvail();
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
