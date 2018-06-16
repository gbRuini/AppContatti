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
import android.widget.Toast;

import com.mobdev.appcontatti.Model.Contatto;

public class EditContact extends AppCompatActivity {

    private EditText mName, mSurname, mPhoneNumber, mEmail, mAddress;
    private String contact_name, contact_surname, contact_mobile, contact_address, contact_email;

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
        mEmail = (EditText) findViewById(R.id.etContactEmail);
        mAddress = (EditText) findViewById(R.id.etAddress);

        Intent intent = getIntent();

        contatto = (Contatto) getIntent().getSerializableExtra("cont");
        contact_name = intent.getStringExtra("name");
        contact_surname = intent.getStringExtra("surname");
        contact_mobile = intent.getStringExtra("number");
        contact_email = intent.getStringExtra("email");
        contact_address = intent.getStringExtra("address");

        Log.d("tag", "nome " + contact_name + " cognome: " + contact_surname);

        mName.setText(contact_name);
        mSurname.setText(contact_surname);
        mPhoneNumber.setText(contact_mobile);
        mEmail.setText(contact_email);
        mAddress.setText(contact_address);


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
            /*
            Intent intent = new Intent(this, ViewContact.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 0);
            return true;
            */
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
                mEmail.getText().toString(),
                mAddress.getText().toString()), contactID);

        Log.d("tag", "Contatto aggiornato");
        Toast.makeText(this, "Contatto Aggiornato", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, ViewContact.class);
        startActivity(intent);
        Log.d("tag", "Il contatto aggiornato è: nome = " + mName.getText().toString() + " cognome = " + mSurname.getText().toString());

    }

    private void backIntent() {
        Intent backIntent = new Intent(this, ViewContact.class);

        backIntent.putExtra("name", mName.getText().toString());
        backIntent.putExtra("surname", mSurname.getText().toString());
        backIntent.putExtra("number", mPhoneNumber.getText().toString());
        backIntent.putExtra("email", mEmail.getText().toString());
        backIntent.putExtra("address", mAddress.getText().toString());

        startActivity(backIntent);
    }

}
