package com.android.nielit.autobolts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UserDetails extends AppCompatActivity {

    public static final String REGISTER_URL = "http://10.0.2.2/dbvolly/vollyRegister.php";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_CITY = "city";
    public static final String KEY_STATE = "state";
    public static final String KEY_PIN = "pin";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";



    private EditText editTextAddr;
    private EditText editTextCity;
    private EditText editTextState;
    private EditText editTextPin;
    private EditText editTextPhone;

    Button btnNext;
    String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnNext = (Button)findViewById(R.id.btnNext);
        editTextAddr = (EditText)findViewById(R.id.txtAddr);
        editTextCity = (EditText)findViewById(R.id.txtCity);
        editTextState = (EditText)findViewById(R.id.txtState);
        editTextPin = (EditText)findViewById(R.id.txtPin);
        editTextPhone = (EditText)findViewById(R.id.txtPhone);
        Intent in= getIntent();
        Bundle b = in.getExtras();
        if(b!=null)
        {
            s =(String)b.get("email");
        }
        btnNextOnClick();
    }

    private void registerUserDetails() {
        final String address = editTextAddr.getText().toString().trim();
        final String city = editTextCity.getText().toString().trim();
        final String state = editTextState.getText().toString().trim();
        final String pin = editTextPin.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(UserDetails.this, response, Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserDetails.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams()  throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_ADDRESS, address);
                params.put(KEY_CITY, city);
                params.put(KEY_STATE, state);
                params.put(KEY_PIN, pin);
                params.put(KEY_PHONE, phone);
                params.put(KEY_EMAIL,s);
                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void btnNextOnClick()
    {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUserDetails();
                Intent intent = new Intent(getApplicationContext(),VehicleDetails.class);
                intent.putExtra("email",s);
                startActivity(intent);
            }
        });
    }
}
