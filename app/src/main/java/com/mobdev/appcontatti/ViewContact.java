package com.mobdev.appcontatti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobdev.appcontatti.Model.Contatto;

public class ViewContact extends AppCompatActivity {

    private TextView cName, cMobile, cAddress, cEmail;
    private ImageButton mPhone;

    private String contact_name, contact_mobile, contact_address, contact_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        cName = findViewById(R.id.contactName);
        cMobile = findViewById(R.id.PhoneInCardView);
        cAddress = findViewById(R.id.AdressInCardView);
        cEmail = findViewById(R.id.emailInCardView);

        // creo contatto che riceve dati dall'intent
        Contatto c = (Contatto) getIntent().getSerializableExtra("contatto");
        Log.d("ViewContact", c.getName() + " " + c.getSurname() + " " + c.getEmail());

        contact_mobile = c.getPhoneNumber().trim();
        contact_name = c.getName() + " " + c.getSurname();
        contact_address = c.getAddress();


        cName.setText(contact_name);
        cMobile.setText(contact_mobile);



    }
}

/*
contatto:
    Adress : nullo

 */
