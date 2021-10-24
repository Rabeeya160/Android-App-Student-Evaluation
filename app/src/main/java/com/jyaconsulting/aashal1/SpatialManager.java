package com.jyaconsulting.aashal1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;

//import com.jyaconsulting.trackaperson.utilinterface.CMResponse;

public class SpatialManager extends Service implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 100000;
    Activity activity;
    boolean canGetLocation = false;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    double latitude;
    Location location;
    protected LocationManager locationManager;
    double longitude;
    private final Context mContext;
//    private GoogleMap mMap;
//    CommunicationManager cm;
//    CMResponse send_current_location = new CMResponse() {
//        @Override
//        public void consumeResponse(String response, boolean issucess) throws JSONException {
//
//        }
//    };

    class C03461 implements OnClickListener {
        C03461() {
        }

        public void onClick(DialogInterface dialog, int which) {
            SpatialManager.this.mContext.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
        }
    }

    class C03472 implements OnClickListener {
        C03472() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    public SpatialManager(Context context, Activity c) {
        this.mContext = context;
        this.activity = c;
//        mMap = gMap;
        getLocation();
    }

    @SuppressLint({"MissingPermission", "WrongConstant"})
    public Location getLocation() {
        try {
            this.locationManager = (LocationManager) this.mContext.getSystemService("location");
            this.isGPSEnabled = this.locationManager.isProviderEnabled("gps");
            this.isNetworkEnabled = this.locationManager.isProviderEnabled("network");
            if (this.isGPSEnabled || this.isNetworkEnabled) {
                this.canGetLocation = true;
                if (this.isNetworkEnabled) {
                    this.locationManager.requestLocationUpdates("network", MIN_TIME_BW_UPDATES, 10.0f, this);
                    Log.d("Network", "Network");
                    if (!(this.locationManager == null || this.location == null)) {
                        this.latitude = this.location.getLatitude();
                        this.longitude = this.location.getLongitude();
                    }
                }
                if (this.isGPSEnabled && this.location == null) {
                    this.locationManager.requestLocationUpdates("gps", MIN_TIME_BW_UPDATES, 10.0f, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (this.locationManager != null) {
                        if (ContextCompat.checkSelfPermission((Activity) this.mContext, "android.permission.ACCESS_FINE_LOCATION") != 0 && ContextCompat.checkSelfPermission((Activity) this.mContext, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                            return null;
                        }
                        if (this.location != null) {
                            this.latitude = this.location.getLatitude();
                            this.longitude = this.location.getLongitude();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.location;
    }

    public void stopUsingGPS() {
        if (this.locationManager != null) {
            this.locationManager.removeUpdates(this);
        }
    }

    public double getLatitude() {
        if (this.location != null) {
            this.latitude = this.location.getLatitude();
        }
        return this.latitude;
    }

    public double getLongitude() {
        if (this.location != null) {
            this.longitude = this.location.getLongitude();
        }
        return this.longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new C03461());
        alertDialog.setNegativeButton("Cancel", new C03472());
        alertDialog.show();

    }

    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        double percesion = location.getAccuracy();
//        return latlng;
//        cm = new CommunicationManager(activity);
//        try {
//            String user_id = Utils.getPreferences(Constant.USER_ID, mContext);
//            JSONObject pram = new JSONObject();
//            pram.put("lat", String.valueOf(latitude));
//            pram.put("long", String.valueOf(longitude));
//            pram.put("percesion", String.valueOf(percesion));
//            pram.put("user_id", user_id);
//            pram.put("location_type","user");
//            cm.postRequest("tap_location_save/",pram,send_current_location);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        if (mMap != null) {
//            LatLng current_loc = new LatLng(latitude, longitude);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_loc, 17));
////            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current_loc, 14.0f));
//            mMap.addMarker(new MarkerOptions().position(current_loc)
//                    .title("Start")
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
////            mMap.moveCamera(CameraUpdateFactory.newLatLng(current_loc));
//        }

//        EditText etLat = (EditText) this.activity.findViewById(R.id.etLatFrmGT);
//        EditText etLong = (EditText) this.activity.findViewById(R.id.etLonFrmGT);
//        EditText etLocAccuracy = (EditText) this.activity.findViewById(R.id.etLocAccuracy);
//        if (location.getAccuracy() < 1000.0f && etLat != null) {
//            etLat.setText("" + this.latitude);
//            etLong.setText("" + this.longitude);
//            etLocAccuracy.setText("" + location.getAccuracy() + " m");
//        }
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public float setAccuracy() {
        return 5.0f;
    }

}
