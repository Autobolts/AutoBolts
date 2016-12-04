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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String REGISTER_URL = "http://10.0.2.2/dbvolly/vollyRegister.php";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_FNAME = "fname";
    public static final String KEY_LNAME = "lname";

    private EditText editTextFname;
    private EditText editTextLname;
    private EditText editTextEmail;
    private EditText editTextPassword;

    Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnReg = (Button)findViewById(R.id.btnReg);
        editTextEmail = (EditText) findViewById(R.id.txtRegEmail);
        editTextPassword = (EditText) findViewById(R.id.txtRegPassword);
        editTextFname = (EditText) findViewById(R.id.txtRegFirstName);
        editTextLname = (EditText) findViewById(R.id.txtRegLastName);
        btnReg.setOnClickListener(this);
    }

    private void registerUser() {
        final String fname = editTextFname.getText().toString().trim();
        final String lname = editTextLname.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams()  throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_FNAME, fname);
                params.put(KEY_LNAME, lname);
                params.put(KEY_EMAIL, email);
                params.put(KEY_PASSWORD, password);
                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == btnReg) {
            registerUser();
            Intent intent = new Intent(getApplicationContext(), UserDetails.class);
            intent.putExtra("email",editTextEmail.getText().toString().trim());
            startActivity(intent);
        }
    }
}
