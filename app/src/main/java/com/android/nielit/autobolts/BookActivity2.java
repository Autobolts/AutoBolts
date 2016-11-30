package com.android.nielit.autobolts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.GregorianCalendar;

public class BookActivity2 extends AppCompatActivity{

    java.util.Calendar calendar = java.util.Calendar.getInstance();
    Button btnDate;
    Button btnBook;
    TextView txtDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnDate = (Button) findViewById(R.id.btnDatePick);
        txtDate = (TextView)findViewById(R.id.txtDate);
        btnBook = (Button)findViewById(R.id.btnBook);
        btnDateOnClick();
        btnBookOnClick();
    }

    public void btnDateOnClick()
    {
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(BookActivity2.this,listener,calendar.get(java.util.Calendar.YEAR),calendar.get(java.util.Calendar.MONTH),calendar.get(java.util.Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void btnBookOnClick()
    {
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BookActivity2.this,"Service Booked",Toast.LENGTH_LONG).show();
            }
        });
    }

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            txtDate.setText(year + "/" + month + "/" + day);
        }
    };
}
