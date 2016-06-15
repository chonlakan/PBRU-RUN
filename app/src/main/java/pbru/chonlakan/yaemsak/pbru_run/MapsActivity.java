package pbru.chonlakan.yaemsak.pbru_run;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //Explicit
    private GoogleMap mMap;
    private double userLatADouble, userLngADouble;
    private LocationManager locationManager;
    private Criteria criteria;
    private String[] userLoginStrings;
    private MyData myData;
    private static final String urlEditLocation = "http://swiftcodingthai.com/pbru3/edit_location.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        userLoginStrings = getIntent().getStringArrayExtra("Login");


        myData = new MyData();

        //Setup Location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);//open service
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);//close altitude Z
        criteria.setBearingRequired(false);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }//Main method

    private class ConnectedLocation extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }//doIn

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }//onPost
    }//ConnectedLocation

    @Override
    protected void onResume() {//ทำหลังหยุดไป
        super.onResume();

        locationManager.removeUpdates(locationListener);

        Location fromISPLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);//ถามไปที่เครือข่ายของผู้ให้บริการ
        if (fromISPLocation != null) {

            userLatADouble = fromISPLocation.getLatitude();
            userLngADouble = fromISPLocation.getLongitude();

        }

        Location fromGPSLocation = myFindLocation(LocationManager.GPS_PROVIDER);//card GPS
        if (fromGPSLocation != null) {

            userLatADouble = fromGPSLocation.getLatitude();
            userLngADouble = fromGPSLocation.getLongitude();

        }

        Log.d("pbruV5", "UserLat ==> " + userLatADouble);
        Log.d("pbruV5", "UserLng ==> " + userLngADouble);
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    public Location myFindLocation(String strProvider) {

        Location location = null;

        if (locationManager.isProviderEnabled(strProvider)) {

            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);
            location = locationManager.getLastKnownLocation(strProvider);

        } else {
            Log.d("pbruV4", "Cannot find location");
        }

        return location;
    }//myFindLocation


    public final LocationListener locationListener = new LocationListener() { //android.location
        @Override
        public void onLocationChanged(Location location) {
            userLatADouble = location.getLatitude();
            userLngADouble = location.getLongitude();

        }//onLocationChange

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Center Location
        LatLng latLng = new LatLng(myData.getLatADouble(),myData.getLngADouble());//ดึงจาก class MyData
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));

        //Ç
        createMyLoop();


    }//onMapReady

    private void createMyLoop() {

        editUserLocationToServer();

        makeAllMarker();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               createMyLoop();

            }
        },3000);
    }

    private void makeAllMarker() {

        mMap.clear();



    }//markAllMarker

    private void editUserLocationToServer() {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("id", userLoginStrings[0])
                .add("Lat", Double.toString(userLatADouble))
                .add("Lng", Double.toString(userLngADouble))
                .build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlEditLocation).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    Log.d("pbruV6", "errorF ==>"+ e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {

                } catch (Exception e) {
                    Log.d("pbruV6", "errorR ==>"+ e.toString());
                }
            }
        });

    }//editUserLocationToServer

}//Main Class
