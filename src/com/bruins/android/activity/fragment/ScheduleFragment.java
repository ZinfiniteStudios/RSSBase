package com.bruins.android.activity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.bruins.android.v2.R;
import com.viewpagerindicator.TitlePageIndicator;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: david.hodge
 * Date: 9/22/13
 * Time: 7:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleFragment extends RoboSherlockFragment {

    private FadingActionBarHelper mFadingHelper;
    private Bundle mArguments;
    public static final String ARG_IMAGE_RES = "image_source";
    public static final String ARG_ACTION_BG_RES = "image_action_bs_res";
    Context mContext;
    View view;
    ViewPager schedulePager;
    TitlePageIndicator scheduleTPI;
    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mtitles;
    VarsitySchedule varsitySchedule = new VarsitySchedule();
    JVSchedule jvSchedule = new JVSchedule();
    AboutFragment aboutFragment = new AboutFragment();
    ScheduleAdapter scheduleAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        view = mFadingHelper.createView(inflater);
        view = inflater.inflate(R.layout.fragment_schedule_view, container, false);

        schedulePager = (ViewPager) view.findViewById(R.id.schedule_view_pager);
        scheduleTPI = (TitlePageIndicator) view.findViewById(R.id.schedule_tpi);

        mtitles = new ArrayList<String>();
        mtitles.add("Varsity");
        mtitles.add("JV");

        mFragments =  new ArrayList<Fragment>();
        mFragments.add(varsitySchedule);
        mFragments.add(jvSchedule);

        scheduleAdapter = new ScheduleAdapter(getActivity(), mtitles, mFragments);

        schedulePager.setAdapter(scheduleAdapter);
        scheduleTPI.setViewPager(schedulePager);
        scheduleTPI.notifyDataSetChanged();
        scheduleTPI.setOnPageChangeListener(scheduleOPCL);

        return view;
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        mFadingHelper = new FadingActionBarHelper()
//                .actionBarBackground(R.drawable.ab_solid_bruins)
//                .headerLayout(R.layout.schedule_header)
//                .parallax(true)
//                .lightActionBar(false)
//                .contentLayout(R.layout.fragment_schedule_view);
//        mFadingHelper.initActionBar(getSherlockActivity());
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getBaseContext();
    }

    class ScheduleAdapter extends FragmentPagerAdapter{
        Context context;
        private LayoutInflater inflater;
        private ArrayList<String> titles;
        private ArrayList<Fragment> mFragments;

        public ScheduleAdapter(Context context, ArrayList<String> strings, ArrayList<Fragment> fragments){
            super(ScheduleFragment.this.getChildFragmentManager());
            this.context = context;
            this.titles = strings;
            this.mFragments = fragments;
            this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return this.titles.size();

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        public void setTitles(ArrayList<String> titles) {
            this.titles = titles;
        }

        public void setFragments(ArrayList<Fragment> fragments) {
            this.mFragments = fragments;
        }
    }

    private ViewPager.OnPageChangeListener scheduleOPCL = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int i) {
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    };
}
