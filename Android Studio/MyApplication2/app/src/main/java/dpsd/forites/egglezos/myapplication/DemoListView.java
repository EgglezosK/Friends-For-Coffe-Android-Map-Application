package dpsd.forites.egglezos.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoListView extends AppCompatActivity {
    private static final long RIPPLE_DURATION = 250;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;

    private Button btn;

    List<GetDataAdapter> GetDataAdapter1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;
public static String selecteduser = "";
    String GET_JSON_DATA_HTTP_URL = "http://egglezos.online/json_parse_listview.php";
    String JSON_IMAGE_TITLE_NAME = "username";
    String JSON_IMAGE_URL = "photo";
    String JSON_USER_EMAIL = "email";
    String JSON_GET_LAT = "lat";
    String JSON_GET_LON ="lon";
    private static DemoListView instance;
    JsonArrayRequest jsonArrayRequest ;


    RequestQueue requestQueue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.demolistview);

        GetDataAdapter1 = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        ButterKnife.bind(this);
        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);
        startService(new Intent(this, GpsService.class));

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        final LinearLayout layout=(LinearLayout) findViewById(R.id.profile_group);
        final LinearLayout listview=(LinearLayout) findViewById(R.id.feed_group);
        final LinearLayout logout=(LinearLayout) findViewById(R.id.settings_group);
        final ImageView icon_feed = (ImageView) findViewById(R.id.icon_listview);
        icon_feed.setImageResource(R.drawable.icon_listview_active);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoListView.this, MapsActivity.class);
                startActivity(intent);
            }
        });






        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = DemoListView.this.getSharedPreferences("tech", DemoListView.this.MODE_PRIVATE);
                settings.edit().clear().commit();
                DemoListView.this.getSharedPreferences("loggedin", 0).edit().clear().commit();
                Intent intent = new Intent(DemoListView.this, Login.class);
                startActivity(intent);

            }
        });

//recyclerView.


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                GetDataAdapter movie = GetDataAdapter1.get(position);
                //Toast.makeText(getApplicationContext(),  movie.getEmail() + " is selected!", Toast.LENGTH_SHORT).show();
                selecteduser = movie.getImageTitleName();
                Intent intent = new Intent(DemoListView.this, InsideUser.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                GetDataAdapter movie = GetDataAdapter1.get(position);
                ImageView imageView = view.findViewById(R.id.imageView3);


                if(imageView.getTag().equals("nolike")){
                    imageView.setTag("liked");
                    imageView.setImageResource(R.drawable.like_enabled);

                    SharedPreferences.Editor editor = getSharedPreferences("LIKES", MODE_PRIVATE).edit();
                    editor.putInt( movie.getImageTitleName(), 1);
                    editor.apply();


                }else{
                    imageView.setTag("nolike");
                    imageView.setImageResource(R.drawable.like);
                    SharedPreferences.Editor editor = getSharedPreferences("LIKES", MODE_PRIVATE).edit();
                    editor.putInt( movie.getImageTitleName(), 2);
                    editor.apply();
                }



                //


            }
        }));


        JSON_DATA_WEB_CALL();





    }




    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            GetDataAdapter GetDataAdapter2 = new GetDataAdapter();

            JSONObject json = null;
            try {


                json = array.getJSONObject(i);



                    GetDataAdapter2.setImageTitleNamee(json.getString(JSON_IMAGE_TITLE_NAME));

                    GetDataAdapter2.setImageServerUrl(json.getString(JSON_IMAGE_URL));

                    GetDataAdapter2.setLONuser(json.getString(JSON_GET_LON));

                    GetDataAdapter2.setLATuser(json.getString(JSON_GET_LAT));

                    GetDataAdapter2.setEmail(json.getString(JSON_USER_EMAIL));





            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, this);



        recyclerView.setAdapter(recyclerViewadapter);
    }


    public static DemoListView getInstance() {
        return instance;
    }

}