package com.mobdev.appcontatti;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mobdev.appcontatti.Model.Contatto;

import static android.content.ContentValues.TAG;

public class AddActivity extends AppCompatActivity {

    private Context mContext = null;

    private static final String TAG = "AddContactFragment";

    // dichiaro i vari elementi di ogni contatto
    private EditText mName, mSurname, mPhoneNumber, mEmail, mAddress;
    private String mImagePath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_fragment);

        this.mContext = this;
        mName = (EditText) findViewById(R.id.etContactName);
        mSurname = (EditText) findViewById(R.id.etContactSurname);
        mPhoneNumber = (EditText) findViewById(R.id.etContactPhone);
        mEmail = (EditText) findViewById(R.id.etContactEmail);
        mAddress = (EditText) findViewById(R.id.etAddress);



        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.action_save) {
            if(mName.getText().toString().isEmpty()){
                Toast.makeText(mContext, "Aggiungi Nome!", Toast.LENGTH_LONG);
                return false;
            }
            else {

                saveContact();
                Log.d(TAG, "contatto salvato");
            }

        }

        if(id == android.R.id.home) {
            //NavUtils.navigateUpFromSameTask(this);
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void saveContact() {

        Log.d(TAG, "Prima del contact manager");
        ContactManager.getInstance(mContext).addContact(new Contatto(mName.getText().toString(), mSurname.getText().toString(), mPhoneNumber.getText().toString(), mEmail.getText().toString(), mAddress.getText().toString()));
        Log.d(TAG, "Dopo del contact manager");
        Toast.makeText(mContext, "Contatto Salvato!", Toast.LENGTH_SHORT);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }







}
