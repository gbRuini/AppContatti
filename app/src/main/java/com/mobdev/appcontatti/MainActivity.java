package com.mobdev.appcontatti;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mobdev.appcontatti.Fragments.ContactsList;
import com.mobdev.appcontatti.Model.Contatto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Main";

    private static final String[] CALL_PERSMISSION = { Manifest.permission.CALL_PHONE };
    private static final String[] MEDIA_PERSMISSION = { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MEDIA_CONTENT_CONTROL };
    private static final String[] CAMERA_PERMISSIONE = { Manifest.permission.CAMERA };

    private ArrayList<Contatto> list;
    private ContactsList chooseFragment = null;
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_activity);

        if (savedInstanceState == null) {
            chooseFragment = new ContactsList();
            getSupportFragmentManager().beginTransaction().add(R.id.container, chooseFragment).commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);

        verifyPermission();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               // Log.d("TAG", "Stai scrivendo nel cerca");
                return false;
            }
        });

        return super.onOptionsItemSelected(item);
    }


    private void verifyPermission(){
        Log.d("tag", "verifyPermission");

        int permissionCall = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int permissionMedia = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCamera = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);


        if (permissionCall != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, CALL_PERSMISSION, 1);
        }

        if (permissionMedia != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, MEDIA_PERSMISSION, 2);
        }

        if(permissionCamera != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, CAMERA_PERMISSIONE, 2);
        }
    }


}
