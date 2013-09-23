package com.bruins.android.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.bruins.android.R;
/**
 * Created with IntelliJ IDEA.
 * User: david.hodge
 * Date: 9/20/13
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationsFragment extends RoboSherlockFragment {

    MapView mapView;
    Bundle bundle;
    GoogleAnalytics mGaInstance;
    GoogleMap mMap;

    private static final LatLng LOCATION_LAT_LON = new LatLng(36.8492,-76.405581);
    private Marker location;
    static final CameraPosition LOC_CAMERA =
            new CameraPosition.Builder().target(LOCATION_LAT_LON)
                    .zoom(17)
                    .bearing(-100)
                    .tilt(25)
                    .build();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        try {
            MapsInitializer.initialize(getActivity());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }


        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(bundle);
        setUpMapIfNeeded(view);
        return view;
    }

    private void setUpMap(){
        mMap.setIndoorEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(LOC_CAMERA));
        addLocationsToMap();
    }

    private void setUpMapIfNeeded(View view){
        if(mMap == null){
            mMap = ((MapView) view.findViewById(R.id.map)).getMap();
            if(mMap != null){
                setUpMap();
            }
        }
    }

    private void addLocationsToMap(){
        location = mMap.addMarker(new MarkerOptions()
                .position(LOCATION_LAT_LON)
                .title(getResources().getString(R.string.location_title))
                .snippet(getResources().getString(R.string.location_content)));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = savedInstanceState;
    }



    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
