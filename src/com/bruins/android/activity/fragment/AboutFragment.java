package com.bruins.android.activity.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;
import com.bruins.android.v2.R;
import android.content.Context;
import roboguice.inject.InjectView;

/**
 * Created with IntelliJ IDEA.
 * User: david.hodge
 * Date: 9/22/13
 * Time: 3:37 AM
 * To change this template use File | Settings | File Templates.
 */

public class AboutFragment extends RoboSherlockFragment {

    ImageView aboutImage;
    ImageView producerLogo;
    ImageView partnerLogo;
    @InjectView(R.id.build_number) TextView buildNumber;
    @InjectView (R.id.build_owner) Button buildOwner;
    Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        aboutImage = (ImageView) view.findViewById(R.id.about_background);
//        producerLogo = (ImageView) view.findViewById(R.id.producer_logo);
//        partnerLogo = (ImageView) view.findViewById(R.id.partner_logo);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getBaseContext();

//        producerLogo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                producerIntentBuilder();
//            }
//        });

//        partnerLogo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                partnerIntentBuilder();
//            }
//        });

        buildNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildDialog();
            }
        });

        buildOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutDialog();
            }
        });
    }

    public void buildDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.open_source_licenses);
        String[] apacheProjects = getResources().getStringArray(R.array.apache_licensed_projects);
        StringBuilder sb = new StringBuilder();
        sb.append(getResources().getString(R.string.licenses_header));
        for (String project : apacheProjects) {
            sb.append("\u2022 ").append(project).append("\n");
        }
        sb.append("\n").append(getResources().getString(R.string.licenses_subheader));
        sb.append(getResources().getString(R.string.apache_2_0_license));
        builder.setMessage(sb.toString());
        builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(true);
        builder.setIcon(android.R.drawable.ic_menu_info_details);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void aboutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
        builder.setTitle("More from David Hodge")
                .setPositiveButton("Other Apps", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent goToMarket = null;
                        goToMarket = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/developer?id=Vapr-Ware"));
                        startActivity(goToMarket);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Website", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent goToMarket = null;
                        goToMarket = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://vapr-ware.com"));
                        startActivity(goToMarket);
                    }
                });
        builder.create();
        builder.show();
    }

    private void producerIntentBuilder(){
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.vapr-ware.com")));
    }

    private void partnerIntentBuilder(){
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.officemax.com")));
    }

}
