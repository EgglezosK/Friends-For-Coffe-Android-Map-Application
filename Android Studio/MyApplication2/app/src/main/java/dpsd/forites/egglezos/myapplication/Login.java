package dpsd.forites.egglezos.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    public static final String LOGIN_URL="http://egglezos.online/login.php";
    public static final String KEY_EMAIL="email";
    public static final String KEY_PASSWORD="password";
    public static final String LOGIN_SUCCESS="success";
    public static final String SHARED_PREF_NAME="tech";
    public static final String EMAIL_SHARED_PREF="email";
    public static String USER_EMAIL = "";
    public static final String LOGGEDIN_SHARED_PREF="loggedin";
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button BtnLogin;
    private boolean loggedIn=false;
    private Button BtnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        VideoView videoview = (VideoView) findViewById(R.id.fullScreenVideoView);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video_back);
        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        editTextEmail=(EditText)findViewById(R.id.editText_email);
        editTextPassword=(EditText)findViewById(R.id.editText_password);
        BtnLogin=(Button)findViewById(R.id.btn_login);
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        BtnRegister=(Button)findViewById(R.id.register);
        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent obj = new Intent(Login.this, Signup.class);
                startActivity(obj);
            }
        });

    }



    private void login() {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equalsIgnoreCase(LOGIN_SUCCESS)){

                            SharedPreferences sharedPreferences = Login.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
                            editor.putString(EMAIL_SHARED_PREF, email);
                            editor.commit();

                            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(Login.this);
                            SharedPreferences.Editor test = settings.edit();
                            test.putString("Useremail", email);
                            test.commit();


                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prams = new HashMap<>();
                prams.put(KEY_EMAIL, email);
                prams.put(KEY_PASSWORD, password);

                return prams;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(LOGGEDIN_SHARED_PREF, false);

        if(loggedIn){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
