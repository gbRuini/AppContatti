package com.mobdev.appcontatti;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdev.appcontatti.Model.Contatto;

public class ViewContact extends AppCompatActivity {

    private TextView cName, cMobile, cAddress, cEmail, cCompany;
    private ImageButton mPhone;
    private CardView mCardEmail;

    private Contatto contatto;

    private String contact_name, contact_surname, contact_mobile, contact_type, contact_address, contact_email, contact_company;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cName = findViewById(R.id.contactName);
        cMobile = findViewById(R.id.PhoneInCardView);
        cEmail = findViewById(R.id.emailInCardView);
        cAddress = findViewById(R.id.etAddress);
        cCompany = findViewById(R.id.companyInCardView);

        mPhone = (ImageButton) findViewById(R.id.phoneCallBtn);


        Intent intent = getIntent();

        contatto = (Contatto) getIntent().getSerializableExtra("cont");
        contact_name = intent.getStringExtra("name");
        contact_surname = intent.getStringExtra("surname");
        contact_mobile = intent.getStringExtra("number");
        contact_type = intent.getStringExtra("type");
        contact_email = intent.getStringExtra("email");
        contact_address = intent.getStringExtra("address");
        contact_company = intent.getStringExtra("company");


        cName.setText(contact_name + " " + contact_surname);
        cEmail.setText(contact_email);
        cMobile.setText(contact_mobile);
        cAddress.setText(contact_address);
        cCompany.setText(contact_company);


        mPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(contact_mobile));
                startActivity(callIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.action_edit) {
            Log.d("tag", "modifico contatto");

            Intent intent = new Intent(this, EditContact.class);

            intent.putExtra("cont", contatto);
            intent.putExtra("name", contact_name);
            intent.putExtra("surname", contact_surname);
            intent.putExtra("number", contact_mobile);
            intent.putExtra("type", contact_type);
            intent.putExtra("email", contact_email);
            intent.putExtra("address", contact_address);
            intent.putExtra("company", contact_company);

            Log.d("tag", "stai passando i dati alla edit, nome: " + contact_name + " cognome: " + contact_surname);


            startActivity(intent);

        }


        if(id == R.id.action_delete) {


            DbHelper helper = new DbHelper(this);
            Cursor cursor = helper.getContactID(contatto);

            int contactID = -1;

              while(cursor.moveToNext()) {
                contactID = cursor.getInt(0);
            }



            helper.deleteContact(contactID);
              Toast.makeText(this, "Contatto Eliminato", Toast.LENGTH_LONG).show();
              Log.d("tag", "Contatto eliminato");



            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

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

}
