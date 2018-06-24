package com.mobdev.appcontatti;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdev.appcontatti.Model.Contatto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class ViewContact extends AppCompatActivity {

    private TextView cName, cMobile, cAddress, cEmail, cCompany;
    private ImageButton mPhone;
    private ImageView cImage,  cImageBusiness;
    private CardView mCardEmail, mCardAddress, mCardCompany, mCardBusinessImage;

    private Contatto contatto;

    private String contact_name, contact_surname, contact_mobile, contact_type, contact_address, contact_email, contact_company, contact_image, contact_business_image;


    private static final String VCF_DIRECTORY = "/vcf_dir";
    private File vcfFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        cName = findViewById(R.id.contactName);
        cMobile = findViewById(R.id.PhoneInCardView);
        cEmail = findViewById(R.id.emailInCardView);
        cAddress = findViewById(R.id.etAddress);
        cCompany = findViewById(R.id.companyInCardView);
        cImage = findViewById(R.id.imageContact);
        cImageBusiness = findViewById(R.id.imageBusinessCard);

        mPhone = (ImageButton) findViewById(R.id.phoneCallBtn);

        mCardAddress = (CardView) findViewById(R.id.addressCard);
        mCardEmail = (CardView) findViewById(R.id.cardViewEmail);
        mCardCompany = (CardView) findViewById(R.id.cardViewCompany);
        mCardBusinessImage = (CardView) findViewById(R.id.cardBusinessCard);



        Intent intent = getIntent();

        contatto = (Contatto) getIntent().getSerializableExtra("cont");
        contact_name = intent.getStringExtra("name");
        contact_surname = intent.getStringExtra("surname");
        contact_mobile = intent.getStringExtra("number");
        contact_type = intent.getStringExtra("type");
        contact_email = intent.getStringExtra("email");
        contact_address = intent.getStringExtra("address");
        contact_company = intent.getStringExtra("company");
        contact_image = intent.getStringExtra("image");
        contact_business_image = intent.getStringExtra("imageBusiness");


        cName.setText(contact_name + " " + contact_surname);
        cMobile.setText(contact_mobile);
        cImage.setImageURI(Uri.parse(contact_image));
        cImageBusiness.setImageURI(Uri.parse(contact_business_image));

        if(contact_email.isEmpty()) {
            mCardEmail.setVisibility(View.GONE);
        } else {
            cEmail.setText(contact_email);
        }

        if(contact_address.isEmpty()) {
            mCardAddress.setVisibility(View.GONE);
        } else {
            cAddress.setText(contact_address);
        }

        if(contact_company.isEmpty()) {
            mCardCompany.setVisibility(View.GONE);
        } else {
            cCompany.setText(contact_company);
        }


        Log.d("tag", "Il path della card businees è: " + contact_business_image);


        if(contact_business_image.equalsIgnoreCase("android.resource://com.mobdev.appcontatti/drawable/card_business")) {
            mCardBusinessImage.setVisibility(View.GONE);
        } else {
            cImageBusiness.setImageURI(Uri.parse(contact_business_image));
        }


        mPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact_mobile));

                    int permissionCall = ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE);
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
            intent.putExtra("image", contact_image);
            intent.putExtra("imageBusiness", contact_business_image);

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


        if(id == R.id.action_share) {
            setVCard();

            String filename = "contact_"+ contact_name.toUpperCase() + "_" + contact_surname.toUpperCase() + ".vcf";

            File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + VCF_DIRECTORY + "/", filename);
           // File filelocation = new File("/storage/emulated/0/vcf_dir/" + filename);
            Uri path = FileProvider.getUriForFile(this, "com.mobdev.appcontatti", filelocation);


            Log.d("tag", "IL PATH E': " + path);

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            //set the type
            emailIntent.setType("text/x-vcard");

           /* String[] to = {"gabriele.ruini@studenti.unipr.it"};
            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
            */

            //the attachment
            emailIntent.putExtra(Intent.EXTRA_STREAM, path);

            //the mail subject
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "vCard Contact");


            startActivity(Intent.createChooser(emailIntent, "Send email..."));


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


    private void setVCard(){
        try {
            File vdfDirectory = new File(Environment.getExternalStorageDirectory() + VCF_DIRECTORY);
            if (!vdfDirectory.exists()) {
                vdfDirectory.mkdirs();
            }

            vcfFile = new File(vdfDirectory, "contact_"+ contact_name.toUpperCase() + "_" + contact_surname.toUpperCase() + ".vcf");

            Log.d("tag", vdfDirectory.getAbsolutePath());

            FileWriter fw = new FileWriter(vcfFile);
            fw.write("BEGIN:VCARD\r\n");
            fw.write("VERSION:3.0\r\n");
            fw.write("N: " + contact_name + ";" + contact_surname + "\r\n");
            fw.write("TEL;TYPE=" + contact_type.toUpperCase() +",VOICE:" + contact_mobile + "\r\n");
            fw.write("ADR:" + contact_address + "\r\n");
            fw.write("EMAIL;TYPE=PREF:" + contact_email + "\r\n");
            fw.write("ORG:" + contact_company + "\r\n");
            fw.write("END:VCARD\r\n");
            fw.close();

            Log.d("tag","Created vCard");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
