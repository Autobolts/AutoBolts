package com.android.nielit.autobolts;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class VehicleDetails extends AppCompatActivity {

    public static final String REGISTER_URL = "http://10.0.2.2/dbvolly/vehiclereg.php";
    public static final String KEY_PURCHASE_DATE = "purchasedate";
    public static final String KEY_LAST_SERVICE = "lastserdate";
    public static final String KEY_ODO_READING = "odoreading";
    public static final String KEY_VEHICLE = "vehiclename";
    public static final String KEY_EMAIL = "email";

    java.util.Calendar calendar = java.util.Calendar.getInstance();
    Button btnEnter;
    Spinner spnMake;
    Spinner spnBrand;
    EditText txtService;
    EditText txtOdo;
    EditText txtPurchase;
    String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicledetails);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnEnter = (Button)findViewById(R.id.btnEnter);
        spnMake = (Spinner)findViewById(R.id.spnMake);
        spnBrand = (Spinner)findViewById(R.id.spnBrand);
        if(spnBrand.getSelectedItem().toString().equalsIgnoreCase("Maruti"))
        {
            String[] values = new String[] {"Swift","Alto-K10"};
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(VehicleDetails.this,android.R.layout.simple_spinner_dropdown_item,values);
            spnMake.setAdapter(arrayAdapter);
        } else if(spnBrand.getSelectedItem().toString().equalsIgnoreCase("Bajaj"))
        {
            String[] values = new String[] {"Pulsar-200ns","Discover-125"};
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(VehicleDetails.this,android.R.layout.simple_spinner_dropdown_item,values);
            spnMake.setAdapter(arrayAdapter);
        } else if(spnBrand.getSelectedItem().toString().equalsIgnoreCase("Honda"))
        {
            String[] values = new String[] {"Accord"};
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(VehicleDetails.this,android.R.layout.simple_spinner_dropdown_item,values);
            spnMake.setAdapter(arrayAdapter);
        } else if(spnBrand.getSelectedItem().toString().equalsIgnoreCase("Ford"))
        {
            String[] values = new String[] {"Figo","Ecosport"};
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(VehicleDetails.this,android.R.layout.simple_spinner_dropdown_item,values);
            spnMake.setAdapter(arrayAdapter);
        }
        txtPurchase =(EditText)findViewById(R.id.txtPurchaseDate);
        txtService = (EditText)findViewById(R.id.txtLastSrvc);
        txtOdo = (EditText)findViewById(R.id.txtOdo);
        Intent in= getIntent();
        Bundle b = in.getExtras();
        if(b!=null)
        {
            s =(String)b.get("email");
        }
        btnEnterOnClick();
        txtPurchaseOnClick();
        txtServiceOnClick();
    }

    private void registerUserDetails() {
        final String purchasedate = spnMake.getSelectedItem().toString();
        final String lastserdate = txtService.getText().toString().trim();
        final String odoreading = txtOdo.getText().toString().trim();
        final String vehiclename = spnMake.getSelectedItem().toString();
        final String email = s;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(VehicleDetails.this, response, Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VehicleDetails.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams()  throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_PURCHASE_DATE, purchasedate);
                params.put(KEY_VEHICLE, vehiclename);
                params.put(KEY_LAST_SERVICE, lastserdate);
                params.put(KEY_ODO_READING, odoreading);
                params.put(KEY_EMAIL, email);
                params.put(KEY_EMAIL,s);
                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void txtPurchaseOnClick()
    {
        txtPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(VehicleDetails.this,listener1,calendar.get(java.util.Calendar.YEAR),calendar.get(java.util.Calendar.MONTH),calendar.get(java.util.Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void txtServiceOnClick()
    {
        txtService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(VehicleDetails.this,listener2,calendar.get(java.util.Calendar.YEAR),calendar.get(java.util.Calendar.MONTH),calendar.get(java.util.Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    public void btnEnterOnClick()
    {
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUserDetails();
                Toast.makeText(VehicleDetails.this,"Vehicle Details Added",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    DatePickerDialog.OnDateSetListener listener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            txtPurchase.setText(year + "/" + month + "/" + day);
        }
    };

    DatePickerDialog.OnDateSetListener listener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            txtService.setText(year + "/" + month + "/" + day);
        }
    };
}
