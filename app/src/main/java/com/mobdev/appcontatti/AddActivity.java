package com.mobdev.appcontatti;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.mobdev.appcontatti.Model.Contatto;

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
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.action_save) {
                saveContact();
                Log.d(TAG, "contatto salvato");

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

        String nome, cognome, numero, email, indirizzo;

        nome = mName.getText().toString();
        cognome = mSurname.getText().toString();
        numero = mPhoneNumber.getText().toString();
        email = mEmail.getText().toString();
        indirizzo = mAddress.getText().toString();

        DbHelper dbHelper = new DbHelper(mContext);
        Contatto contatto = new Contatto(nome, cognome, numero, email, indirizzo);

        if(dbHelper.addContact(contatto)) {
            Toast.makeText(mContext, "Contatto Salvato!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Errore nel salvataggio", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }



}
