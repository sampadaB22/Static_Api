package com.example.staticapi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    String url = "http://192.168.0.108:3000/user";
    TextView name, mobile, address,err;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        address = findViewById(R.id.address);

        getUser();

    }

    private void getUser() {
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("MeraError", "onResponse: " + response);
                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    String fullName = response.getString("firstName")+response.getString("lastName");
                    String getMobile = response.getString("mobile");
                    String getAddress = response.getString("address");

                    name.setText(fullName);
                    address.setText(getAddress);
                    mobile.setText(getMobile);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(objectRequest);
    }
}