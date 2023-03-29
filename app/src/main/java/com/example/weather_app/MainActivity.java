package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    ImageButton imageButton;
    TextView country_yt, city_yt, temp_yt, lat_ty, long_ty, humid_ty, wind_ty, sunrise, sunset, pressure, logout;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        country_yt = findViewById(R.id.country);
        city_yt = findViewById(R.id.city);
        temp_yt =findViewById(R.id.textView4);
        imageButton = findViewById(R.id.imageButton3);
        lat_ty = findViewById(R.id.latitude);
        long_ty = findViewById(R.id.longitude);
        humid_ty = findViewById(R.id.humidity);
        wind_ty = findViewById(R.id.wind);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        pressure = findViewById(R.id.pressure);
        logout = findViewById(R.id.logout_btn);

        mAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findWeather();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            }
        });
    }

        public void findWeather(){
            String city = editText.getText().toString();
            String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=0d4f328d2351ca56d57ee536520afd7d";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d("cities json", response);

                        //find country
                        JSONObject object1 = jsonObject.getJSONObject("sys");
                        String country_find = object1.getString("country");
                        country_yt.setText(country_find);

                        //find city
                        String city_find = jsonObject.getString("name");
                        city_yt.setText(city_find);

                        //find temperature
                        JSONObject object2 = jsonObject.getJSONObject("main");
                        String temp_find = object2.getString("temp");
                        float temp = Float.parseFloat(temp_find) - 273;
                        String temp_str = String.format("%.02f", temp);
                        temp_yt.setText(temp_str+"\u2103");

                        //find icon
                        JSONArray jsonArray = jsonObject.getJSONArray("weather");
                        JSONObject obj = jsonArray.getJSONObject(0);
                        String icon = obj.getString("icon");
                        Picasso.get().load("http://openweathermap.org/img/win"+icon+"@2x.png").into(imageButton);

                        //find latitude
                        JSONObject object3 = jsonObject.getJSONObject("coord");
                        String find_latitude = object3.getString("lat");
                        lat_ty.setText(find_latitude);

                        //find humidity
                        String humidity_find = object2.getString("humidity");
                        humid_ty.setText(humidity_find);

                        //find wind speed
                        JSONObject object4 = jsonObject.getJSONObject("wind");
                        String speed_find = object4.getString("speed");
                        wind_ty.setText(speed_find);

                        //find longitude
                        String find_long = object3.getString("lon");
                        long_ty.setText(find_long);

                        //find pressure
                        String pressure_find = object2.getString("humidity");
                        pressure.setText(pressure_find);

                        //find sunset
                        String sunset_find = object1.getString("sunset");
                        sunset.setText(sunset_find);

                        //find sunrise
                        String sunrise_find = object1.getString("sunrise");
                        sunrise.setText(sunrise_find);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);

        }
}