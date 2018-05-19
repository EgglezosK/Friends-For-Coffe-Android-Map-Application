package dpsd.forites.egglezos.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.ion.Ion;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final long RIPPLE_DURATION = 250;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;

    private GoogleMap mMap;
    private RequestQueue mQueue;
    String finaldistance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ButterKnife.bind(this);





        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        final LinearLayout layout=(LinearLayout) findViewById(R.id.feed_list);
        final LinearLayout listview=(LinearLayout) findViewById(R.id.feed_group);
        final LinearLayout logout=(LinearLayout) findViewById(R.id.settings_group);
        final ImageView icon_feed = (ImageView) findViewById(R.id.icon_map);
        icon_feed.setImageResource(R.drawable.icon_mapview_active);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });




        listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MapsActivity.this, DemoListView.class);
                startActivity(intent);

            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = MapsActivity.this.getSharedPreferences("tech", MapsActivity.this.MODE_PRIVATE);
                settings.edit().clear().commit();
                MapsActivity.this.getSharedPreferences("loggedin", 0).edit().clear().commit();
                Intent intent = new Intent(MapsActivity.this, Login.class);
                startActivity(intent);

            }
        });

        mQueue = Volley.newRequestQueue(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.google_map_style));

            if (!success) {
               // Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
           // Log.e(TAG, "Can't find style. Error: ", e);
        }

        jsonParse();

    }

    private void jsonParse() {

        String url = "http://egglezos.online/json_exp.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("signup");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);

                                String firstName = employee.getString("username");
                                String lat = employee.getString("lat");
                                String lon = employee.getString("lon");
                                String mail = employee.getString("email");
                                String photo = employee.getString("photo");


                                double final_lat = Double.parseDouble(lat);
                                double final_lon  = Double.parseDouble(lon);

                                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MapsActivity.this);
                                final String useremail = settings.getString("Useremail", "");
                                GPSTracker gps = new GPSTracker(MapsActivity.this);
                                final double latitude = gps.getLatitude();
                                double longitude = gps.getLongitude();

                                if(useremail.equals(mail)){

                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(final_lat, final_lon)));
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 17.0f));

                                    try {
                                        Bitmap bmImg = Ion.with(MapsActivity.this).load(photo).asBitmap().get();
                                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bmImg , bmImg .getWidth()*3, bmImg .getHeight()*3, false);
                                        mMap.addMarker(new MarkerOptions().position(new LatLng(final_lat, final_lon)).title(firstName + "( You )").icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)));
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }


                                }else{
                                    double test = distance(latitude,longitude,final_lat,final_lon);
                                   /* if(test < 1){
                                        test =Double.parseDouble(new DecimalFormat("##.####").format(test));

                                    }
                                    */
                                   String finaldistance  = String.valueOf(new DecimalFormat("##.###").format(test));
                                    try {
                                        Bitmap bmImg = Ion.with(MapsActivity.this).load(photo).asBitmap().get();
                                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bmImg , bmImg .getWidth()*3, bmImg .getHeight()*3, false);
                                        mMap.addMarker(new MarkerOptions().position(new LatLng(final_lat, final_lon)).title(firstName + " D: " + finaldistance + "km").icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)));
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }



                                    }

                                //mTextViewResult.append(firstName + ", " + lat + ", " + mail + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
