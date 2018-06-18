package com.mobdev.appcontatti;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobdev.appcontatti.Model.Contatto;

public class EditContact extends AppCompatActivity {

    private EditText mName, mSurname, mPhoneNumber, mEmail, mAddress, mCompany;
    private Spinner mSpinner;
    private String contact_name, contact_surname, contact_mobile, contact_type, contact_address, contact_email, contact_company;

    private Contatto contatto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    //    getSupportActionBar().setDisplayShowHomeEnabled(true);

        mName = (EditText) findViewById(R.id.etContactName);
        mSurname = (EditText) findViewById(R.id.etContactSurname);
        mPhoneNumber = (EditText) findViewById(R.id.etContactPhone);
        mSpinner = (Spinner) findViewById(R.id.selectDevice);
        mEmail = (EditText) findViewById(R.id.etContactEmail);
        mAddress = (EditText) findViewById(R.id.etAddress);
        mCompany = (EditText) findViewById(R.id.etCompany);

        Intent intent = getIntent();

        contatto = (Contatto) getIntent().getSerializableExtra("cont");
        contact_name = intent.getStringExtra("name");
        contact_surname = intent.getStringExtra("surname");
        contact_mobile = intent.getStringExtra("number");
        contact_type = intent.getStringExtra("type");
        contact_email = intent.getStringExtra("email");
        contact_address = intent.getStringExtra("address");
        contact_company = intent.getStringExtra("company");

        Log.d("tag", "nome " + contact_name + " cognome: " + contact_surname);

        mName.setText(contact_name);
        mSurname.setText(contact_surname);
        mPhoneNumber.setText(contact_mobile);
        mEmail.setText(contact_email);
        mAddress.setText(contact_address);
        mCompany.setText(contact_company);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_contact, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.action_save) {
            Log.d("tag", "contatto modificato");
            saveAndUpdate();
            backIntent();

        }

        if(id == android.R.id.home) {
            //NavUtils.navigateUpFromSameTask(this);

            backIntent();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }


    private void saveAndUpdate() {

        DbHelper helper = new DbHelper(this);
        Cursor cursor = helper.getContactID(contatto);

        int contactID = -1;

        while(cursor.moveToNext()) {
            contactID = cursor.getInt(0);
        }

        helper.updateContact(new Contatto(
                mName.getText().toString(),
                mSurname.getText().toString(),
                mPhoneNumber.getText().toString(),
                mSpinner.getSelectedItem().toString(),
                mEmail.getText().toString(),
                mAddress.getText().toString(),
                mCompany.getText().toString()), contactID);


        Log.d("tag", "Contatto aggiornato");
        Toast.makeText(this, "Contatto Aggiornato", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, ViewContact.class);
        startActivity(intent);
        Log.d("tag", "Il contatto aggiornato è: nome = " + mName.getText().toString() + " cognome = " + mSurname.getText().toString() + " tipo: " + mSpinner.getSelectedItem().toString());

    }

    private void backIntent() {
        Intent backIntent = new Intent(this, ViewContact.class);

        backIntent.putExtra("name", mName.getText().toString());
        backIntent.putExtra("surname", mSurname.getText().toString());
        backIntent.putExtra("number", mPhoneNumber.getText().toString());
        backIntent.putExtra("type", mSpinner.getSelectedItem().toString());
        backIntent.putExtra("email", mEmail.getText().toString());
        backIntent.putExtra("address", mAddress.getText().toString());
        backIntent.putExtra("company", mCompany.getText().toString());

        startActivity(backIntent);
    }

}
