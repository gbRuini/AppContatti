package com.mobdev.appcontatti;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobdev.appcontatti.Model.Contatto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private Context mContext = null;

    private static final String TAG = "AddContactFragment";

    // dichiaro i vari elementi di ogni contatto
    private EditText mName, mSurname, mPhoneNumber, mEmail, mAddress, mCompany;
    private Spinner mSpinner;
    private String mImagePath;


    // photo
    private Uri imageCaptureUri;
    private ImageView mProfileImage;
    private ImageButton choosePhotoBtn;
    private static final int CAMERA = 2;
    private static final int GALLERY = 3;
    private static final String IMAGE_DIRECTORY = "/Download";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_fragment);

        this.mContext = this;
        mName = (EditText) findViewById(R.id.etContactName);
        mSurname = (EditText) findViewById(R.id.etContactSurname);
        mPhoneNumber = (EditText) findViewById(R.id.etContactPhone);
        mSpinner = (Spinner) findViewById(R.id.selectDevice);
        mEmail = (EditText) findViewById(R.id.etContactEmail);
        mAddress = (EditText) findViewById(R.id.etAddress);
        mCompany = (EditText) findViewById(R.id.etCompany);



        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mProfileImage = (ImageView) findViewById(R.id.profileImage);
        choosePhotoBtn = (ImageButton) findViewById(R.id.choosePhotoBtn);

        choosePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag", "cliccata image camera");
               showPictureDialog();
            }
        });

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            if(requestCode == CAMERA) {
                Bundle bundle = data.getExtras();
                final Bitmap bitmap = (Bitmap) bundle.get("data");
                mProfileImage.setImageBitmap(bitmap);
            }
            if(requestCode == GALLERY) {
                Uri selectedImageUri = data.getData();
                mProfileImage.setImageURI(selectedImageUri);
            }
        }


    /*
        if (requestCode == PICK_FROM_FILE) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    prIM.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }


            }

        } else if (requestCode == PICK_FROM_CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            prIM.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    */
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

    // salva contatto nel db
    private void saveContact() {

        String nome, cognome, numero, tipo, email, indirizzo, azienda;

        nome = mName.getText().toString();
        cognome = mSurname.getText().toString();
        numero = mPhoneNumber.getText().toString();
        tipo = mSpinner.getSelectedItem().toString();
        email = mEmail.getText().toString();
        indirizzo = mAddress.getText().toString();
        azienda = mCompany.getText().toString();

        if(nome.isEmpty()) {
            Toast.makeText(this, "Inserisci il nome!", Toast.LENGTH_LONG).show();
            return;
        }

        if(cognome.isEmpty()) {
            Toast.makeText(this, "Inserisci il cognome!", Toast.LENGTH_LONG).show();
            return;
        }

        if(numero.isEmpty()) {
            Toast.makeText(this, "Inserisci il numero!", Toast.LENGTH_LONG).show();
            return;
        }

        DbHelper dbHelper = new DbHelper(mContext);
        Contatto contatto = new Contatto(nome, cognome, numero, tipo, email, indirizzo, azienda);

        if(dbHelper.addContact(contatto)) {
            Toast.makeText(mContext, "Contatto Salvato!", Toast.LENGTH_SHORT).show();
            Log.d("tag", "Il tipo del contatto salvato è: " + tipo);
        } else {
            Toast.makeText(mContext, "Errore nel salvataggio", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


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
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }





    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

}




