package dpsd.forites.egglezos.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yalantis.guillotine.animation.GuillotineAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    private static final long RIPPLE_DURATION = 250;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;

    private Button btn;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //json listview

        /*
        btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DemoListView.class);
                startActivity(intent);
            }
        });
*/


        //end jsonlistview



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
        final ImageView icon_feed = (ImageView) findViewById(R.id.icon_feed);
        icon_feed.setImageResource(R.drawable.feed_icon_list_active);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });




        listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, DemoListView.class);
                startActivity(intent);

            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = MainActivity.this.getSharedPreferences("tech", MainActivity.this.MODE_PRIVATE);
                settings.edit().clear().commit();
                MainActivity.this.getSharedPreferences("loggedin", 0).edit().clear().commit();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);

            }
        });





    }


    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){

        if (keyCode==KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder alertbox=new AlertDialog.Builder(MainActivity.this);
            alertbox.setTitle("Do you really want to exit?");
            alertbox.setCancelable(false);
            alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertbox.show();
        }
        return super.onKeyDown(keyCode,event);
    }






}
