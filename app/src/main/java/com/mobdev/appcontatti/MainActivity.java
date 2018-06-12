package com.mobdev.appcontatti;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Main";

    private ContactsList chooseFragment = null;


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



    /*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    private void openMainActivity(){

        Intent newIntent = new Intent(new Intent(this,MainActivity.class));
        startActivity(newIntent);
    }

*/

}
