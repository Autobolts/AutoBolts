package com.android.nielit.autobolts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView imgPeriodic;
    ImageView imgBook;
    ImageView imgDetail;
    SessionManagement sessionManagement;
    TextView txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //sessionManagement = new SessionManagement(getApplicationContext());
        imgPeriodic = (ImageView)findViewById(R.id.imgPeriodic);
        imgBook = (ImageView)findViewById(R.id.imgBook);
        imgDetail = (ImageView)findViewById(R.id.imgDetail);
        txtEmail = (TextView)findViewById(R.id.txtLoginEmail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*sessionManagement.checkLogin();
        HashMap<String, String> user = sessionManagement.getUserDetails();
        String email = user.get(SessionManagement.KEY_EMAIL);
        txtEmail.setText(Html.fromHtml(email));*/
        imgPOnClick();
        imgBOnClick();
        imgDOnClick();
    }

    public void imgPOnClick()
    {
        imgPeriodic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PeriodicActivity.class);
                startActivity(intent);
            }
        });
    }

    public void imgBOnClick()
    {
        imgBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BookActivity.class);
                startActivity(intent);
            }
        });
    }

    public void imgDOnClick()
    {
        imgDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_pchart) {
            Intent intent = new Intent(getApplicationContext(),PeriodicActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_book) {
            Intent intent = new Intent(getApplicationContext(),BookActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_details) {
            Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(getApplicationContext(),HelpActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_signout) {
            //sessionManagement.logoutUser();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
