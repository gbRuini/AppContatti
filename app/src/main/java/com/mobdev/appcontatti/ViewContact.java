package com.mobdev.appcontatti;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdev.appcontatti.Fragments.EditFragment;
import com.mobdev.appcontatti.Model.Contatto;

import java.io.Serializable;

public class ViewContact extends AppCompatActivity {

    private TextView cName, cMobile, cAddress, cEmail;
    private ImageButton mPhone;
    private CardView mCardEmail;

    private Contatto contatto;

    private EditFragment editFragment = null;

    private String contact_name, contact_surname, contact_mobile, contact_address, contact_email;


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
        // cAddress = findViewById(R.id.visual_indirizzo);


        Intent intent = getIntent();

        contatto = (Contatto) getIntent().getSerializableExtra("cont");
        contact_name = intent.getStringExtra("name");
        contact_surname = intent.getStringExtra("surname");
        contact_mobile = intent.getStringExtra("number");
        contact_email = intent.getStringExtra("email");
       // contact_address = intent.getStringExtra("address");


        cName.setText(contact_name + " " + contact_surname);
        cEmail.setText(contact_email);
        cMobile.setText(contact_mobile);
       // cAddress.setText(contact_address);

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
            
            /*
            chooseFragment = new ContactsList();
            getSupportFragmentManager().beginTransaction().add(R.id.container, chooseFragment).commit();
            Intent intent = new Intent(this, EditContact.class);
            startActivity(intent);

            */

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
           /* }
            else {
                Toast.makeText(this, "Errore!", Toast.LENGTH_LONG).show();
                Log.d("tag", "Errore");
            }
*/


            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


        if(id == android.R.id.home) {
            //NavUtils.navigateUpFromSameTask(this);
           /* Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 0);*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
