package com.mobdev.appcontatti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobdev.appcontatti.Model.Contatto;

public class ViewContact extends AppCompatActivity {

    private TextView cName, cMobile, cAddress, cEmail;
    private ImageButton mPhone;
    private CardView mCardEmail;

    private String contact_name, contact_surname, contact_mobile, contact_address, contact_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizzazione_contatto);

        cName = findViewById(R.id.visual_nome);
        cMobile = findViewById(R.id.visual_mobile);
        cEmail = findViewById(R.id.visual_email);
        cAddress = findViewById(R.id.visual_indirizzo);


        Intent intent = getIntent();

        contact_name = intent.getStringExtra("name");
        contact_surname = intent.getStringExtra("surname");
        contact_mobile = intent.getStringExtra("number");
        contact_email = intent.getStringExtra("email");
        contact_address = intent.getStringExtra("address");


        cName.setText(contact_name + " " + contact_surname);
        cEmail.setText(contact_email);
        cMobile.setText(contact_mobile);
        cAddress.setText(contact_address);



    }
}

/*
contatto:
    Adress : nullo

 */
