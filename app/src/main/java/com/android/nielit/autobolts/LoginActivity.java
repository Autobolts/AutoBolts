package com.android.nielit.autobolts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
public static final String LOGIN_URL="http://10.0.2.2/dbvolly/login.php";
    Button btnLogin;
    Button btnPwd;
    Button btnSignUp;
    public static final String KEY_EMAIL="email";
    public static final String KEY_PASSWORD="password";
    public EditText editTextEmail;
    public EditText editTextPassword;
    public String email;
    public String password;
    SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //sessionManagement = new SessionManagement(getApplicationContext());
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnSignUp = (Button)findViewById(R.id.btnSingup);
        btnLogin.setOnClickListener(this);
        btnRegOnClick();
        editTextEmail=(EditText)findViewById(R.id.txtUsername);
        editTextPassword=(EditText)findViewById(R.id.txtRegPassword);
    }

    public void userLogin()
    {

                email=editTextEmail.getText().toString().trim();
                password=editTextPassword.getText().toString().trim();
                StringRequest stringRequest = new StringRequest(Request.Method.POST,LOGIN_URL,new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response)
                    {
                        if(response.trim().equals("success"))
                        {
                            openProfile();
                        }else
                        {
                            Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show();

                            }
                        }){
                @Override
                protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String,String> map=new HashMap<String, String>();
                    map.put(KEY_EMAIL,email);
                    map.put(KEY_PASSWORD,password);
                    return map;
                }
                };

                RequestQueue requestQueue= Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);

            }

        private void openProfile()
    {
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View v)
    {
        userLogin();
    }


    public void btnRegOnClick()
    {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
