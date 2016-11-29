package com.android.nielit.autobolts;

import android.*;
import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class BookActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    GoogleMap mGoogleMap;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (googleServiceAvailable()) {
            Toast.makeText(this, "Connected to Play Services", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_book);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.book_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            initMap();

        } else {
            setContentView(R.layout.activity_home);
        }
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

    }

    public boolean googleServiceAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int isAvailable = apiAvailability.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (apiAvailability.isUserResolvableError(isAvailable)) {
            Dialog dialog = apiAvailability.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to Play Services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.book_layout);
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
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_pchart) {
            Intent intent = new Intent(getApplicationContext(), PeriodicActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_book) {
            Intent intent = new Intent(getApplicationContext(), BookActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_details) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_signout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.book_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if(Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                List<String> permissionsNeeded = new ArrayList<>();

                final List<String> permissionsList = new ArrayList<String>();
                if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
                    permissionsNeeded.add("Fine Location");
                if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
                    permissionsNeeded.add("Course Location");

                if (permissionsList.size() > 0) {
                    if (permissionsNeeded.size() > 0) {
                        String message = "You need to grant access to " + permissionsNeeded.get(0);
                        for (int i = 1; i < permissionsNeeded.size(); i++)
                            message = message + " and " + permissionsNeeded.get(i);
                        showMessageOKCancel(message,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 124);
                                    }
                                });
                        return;
                    }
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            124);
                    return;
                }
            }
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick()
            {
                Button btnService = (Button)findViewById(R.id.btnLocate);
                btnService.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);

            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
        mGoogleMap.moveCamera(update);
    }

    public void geoLocate(View view)
    {
        EditText txtLoc = (EditText)findViewById(R.id.txtLoc);
        String location = txtLoc.getText().toString();

        try{
            Geocoder gc = new Geocoder(this);
            List<android.location.Address> list = gc.getFromLocationName(location, 1);
            android.location.Address address = list.get(0);

            double lat = address.getLatitude();
            double lng = address.getLongitude();
            goToLocationZoom(lat, lng, 14.0f);
            Button btnService = (Button)findViewById(R.id.btnLocate);
            btnService.setVisibility(View.VISIBLE);
        }
        catch (Exception ex) {
            Toast.makeText(this, "Enter a region", Toast.LENGTH_SHORT).show();
        }
    }

    public void serviceLocate(View view)
    {
        CameraUpdate update = CameraUpdateFactory.zoomBy(-3.0f);
        mGoogleMap.moveCamera(update);
        if (marker != null) {
            marker.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions()
                .title("Bajaj RE Service Centre")
                .position(new LatLng(11.2838987, 75.7690573));
        marker = mGoogleMap.addMarker(markerOptions);

        String[] values = new String[] {markerOptions.getTitle().toString()};
        ListView listView = (ListView)findViewById(R.id.lstView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.list_black_text,R.id.list_content, values);
        listView.setAdapter(arrayAdapter);
    }

    public void btnBookClick(View view)
    {
                Intent intent = new Intent(BookActivity.this,BookActivity2.class);
                startActivity(intent);
    }
}
