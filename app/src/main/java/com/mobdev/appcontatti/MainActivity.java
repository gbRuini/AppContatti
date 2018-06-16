package com.mobdev.appcontatti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mobdev.appcontatti.Fragments.ContactsList;
import com.mobdev.appcontatti.Model.Contatto;
import com.mobdev.appcontatti.Model.DataToActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Main";

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



/*
    private void filter(String text) {
        ArrayList<Contatto> filteredList = new ArrayList<>();

        for(Contatto item : list) {
            if(item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        myAdapter.filterList(filteredList);
    }
*/

}
