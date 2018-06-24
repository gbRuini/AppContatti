package com.mobdev.appcontatti;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobdev.appcontatti.Model.Contatto;

import java.io.IOException;

public class EditContact extends AppCompatActivity {

    private EditText mName, mSurname, mPhoneNumber, mEmail, mAddress, mCompany;
    private Spinner mSpinner;
    private String contact_name, contact_surname, contact_mobile, contact_type, contact_address, contact_email, contact_company, contact_image, contact_business_image;

    private Contatto contatto;

    private ImageButton mAddBusiness;
    private int choose;

    // photo
    private Uri mImagePath = Uri.parse("android.resource://com.mobdev.appcontatti/drawable/empty_contact");
    private Uri mBusinessPath = Uri.parse("android.resource://com.mobdev.appcontatti/drawable/empty_contact");
    private ImageView mProfileImage;
    private ImageButton choosePhotoBtn;
    private static final int CAMERA = 2;
    private static final int GALLERY = 3;
    private ImageView mBusinessImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mName = (EditText) findViewById(R.id.etContactName);
        mSurname = (EditText) findViewById(R.id.etContactSurname);
        mPhoneNumber = (EditText) findViewById(R.id.etContactPhone);
        mSpinner = (Spinner) findViewById(R.id.selectDevice);
        mEmail = (EditText) findViewById(R.id.etContactEmail);
        mAddress = (EditText) findViewById(R.id.etAddress);
        mCompany = (EditText) findViewById(R.id.etCompany);

        mProfileImage = (ImageView) findViewById(R.id.profileImage);
        choosePhotoBtn = (ImageButton) findViewById(R.id.choosePhotoBtn);

        choosePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag", "cliccata image camera");
                showPictureDialog();
                choose = 0;
            }
        });
        mProfileImage.setImageURI(mImagePath);

        mBusinessImage = (ImageView) findViewById(R.id.imageBusinessCard);
        mAddBusiness = (ImageButton) findViewById(R.id.addBusiness);

        mAddBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
                choose = 1;
            }
        });

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

        Log.d("tag", "nome " + contact_name + " cognome: " + contact_surname);

        mName.setText(contact_name);
        mSurname.setText(contact_surname);
        mPhoneNumber.setText(contact_mobile);
        mEmail.setText(contact_email);
        mAddress.setText(contact_address);
        mCompany.setText(contact_company);
        mProfileImage.setImageURI(Uri.parse(contact_image));
        mBusinessImage.setImageURI(Uri.parse(contact_business_image));


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        switch (choose) {
            case 0:
                if (requestCode == CAMERA) {
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    mProfileImage.setImageBitmap(thumbnail);
                }
                if (requestCode == GALLERY) {
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        try {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                            mImagePath = selectedImageUri;
                            mProfileImage.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
                break;
            case 1:
                if (requestCode == CAMERA) {
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    mBusinessImage.setImageBitmap(thumbnail);
                }
                if (requestCode == GALLERY) {
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        try {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                            mBusinessPath = selectedImageUri;
                            mBusinessImage.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
                break;

        }
    }


    //  Imposto foto profilo

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select Photo From Gallery",
                "Capture Photo From Camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        startActivityForResult(cameraIntent, CAMERA);
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
                mCompany.getText().toString(),
                mImagePath.toString(),
                mBusinessPath.toString()), contactID);


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
        backIntent.putExtra("image", mImagePath.toString());
        backIntent.putExtra("imageBusiness", mBusinessPath.toString());

        startActivity(backIntent);
    }

}
