package dpsd.forites.egglezos.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
public class InsideUser extends AppCompatActivity {
    public String finaluser = DemoListView.selecteduser;

    private static final long RIPPLE_DURATION = 250;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;

    private RequestQueue mQueue;

    public TextView text ;
public ImageView img;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);



        ButterKnife.bind(this);

text =  (TextView) findViewById(R.id.inside_text);
img = (ImageView) findViewById(R.id.inside_user_photo) ;


        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        final LinearLayout layout=(LinearLayout) findViewById(R.id.profile_group);
        final LinearLayout listview=(LinearLayout) findViewById(R.id.feed_group);
        final LinearLayout listviewmain=(LinearLayout) findViewById(R.id.feed_list);
        final LinearLayout logout=(LinearLayout) findViewById(R.id.settings_group);
        //final ImageView icon_feed = (ImageView) findViewById(R.id.icon_map);
        //icon_feed.setImageResource(R.drawable.icon_mapview_active);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsideUser.this, MapsActivity.class);
                startActivity(intent);
            }
        });



        listviewmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsideUser.this, MainActivity.class);
                startActivity(intent);
            }
        });




        listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InsideUser.this, DemoListView.class);
                startActivity(intent);

            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = InsideUser.this.getSharedPreferences("tech", InsideUser.this.MODE_PRIVATE);
                settings.edit().clear().commit();
                InsideUser.this.getSharedPreferences("loggedin", 0).edit().clear().commit();
                Intent intent = new Intent(InsideUser.this, Login.class);
                startActivity(intent);

            }
        });

        mQueue = Volley.newRequestQueue(this);


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


                               // SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(InsideUser.this);
                               // final String useremail = settings.getString("Useremail", "");



                                if(firstName.equals(finaluser)){

                                text.setText(mail);
                                    Picasso.get().load(photo).into(img);



                                }else{



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
}
