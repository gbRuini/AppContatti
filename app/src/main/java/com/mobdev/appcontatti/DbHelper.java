package com.mobdev.appcontatti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mobdev.appcontatti.Model.Contatto;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.mobdev.appcontatti.Model.DbContract.COLUMN_ADDRESS;
import static com.mobdev.appcontatti.Model.DbContract.COLUMN_COMPANY;
import static com.mobdev.appcontatti.Model.DbContract.COLUMN_EMAIL;
import static com.mobdev.appcontatti.Model.DbContract.COLUMN_NAME;
import static com.mobdev.appcontatti.Model.DbContract.COLUMN_NUMBER;
import static com.mobdev.appcontatti.Model.DbContract.COLUMN_SURNAME;
import static com.mobdev.appcontatti.Model.DbContract.COLUMN_TYPE;
import static com.mobdev.appcontatti.Model.DbContract.DATABASE_NAME;
import static com.mobdev.appcontatti.Model.DbContract.TABLE_NAME;
import static com.mobdev.appcontatti.Model.DbContract._ID;

/**
 * Created by gabriele on 13/06/18.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME +
                " ( " +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_SURNAME + " TEXT, " +
                COLUMN_NUMBER + " VARCHAR(15), " +
                COLUMN_TYPE + " VARCHAR(15), " +
                COLUMN_EMAIL + " VARCHAR(100), " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_COMPANY + " TEXT " +
                " )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // inserisce nuovo contatto nel database
    public boolean addContact(Contatto contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, contact.getName());
        contentValues.put(COLUMN_SURNAME, contact.getSurname());
        contentValues.put(COLUMN_NUMBER, contact.getPhoneNumber());
        contentValues.put(COLUMN_TYPE, contact.getTypeNumber());
        contentValues.put(COLUMN_EMAIL, contact.getEmail());
        contentValues.put(COLUMN_ADDRESS, contact.getAddress());
        contentValues.put(COLUMN_COMPANY, contact.getCompany());

        //  contentValues.put(COL6, contact.getImageContact());


        Log.d("tag", "nome: " + contact.getName() + " cognome: " + contact.getSurname() +
                " numero: " + contact.getPhoneNumber() + " email:" + contact.getEmail() +
                " indirizzo: " + contact.getAddress() + " azienda: " + contact.getCompany());


        // restituisce risultato dalla creazione del db
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1) {
            return false;
        } else {
            return true;
        }

    }

    // riceve tutti i contatti dal db
    public Cursor getAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }


    // aggiorna contatto tramite id
    public boolean updateContact(Contatto contact, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, contact.getName());
        contentValues.put(COLUMN_SURNAME, contact.getSurname());
        contentValues.put(COLUMN_NUMBER, contact.getPhoneNumber());
        contentValues.put(COLUMN_TYPE, contact.getTypeNumber());
        contentValues.put(COLUMN_EMAIL, contact.getEmail());
        contentValues.put(COLUMN_ADDRESS, contact.getAddress());
        contentValues.put(COLUMN_COMPANY, contact.getCompany());

        //  contentValues.put(COL6, contact.getImageContact());


        int update = db.update(TABLE_NAME, contentValues, _ID + " = ? ", new String[] {String.valueOf(id)});

        if(update != 1) {
            return false;
        } else {
            return true;
        }
    }

    // restitusce l'id unico del contatto dal database
    public Cursor getContactID(Contatto contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_NAME + " = '" + contact.getName() + "'" +
                " AND " + COLUMN_NUMBER + " = '" + contact.getPhoneNumber() + "'";
        return db.rawQuery(sql, null);
    }


    // cancella contatto tramite id
    public int deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id_ = ?",  new String[] {String.valueOf(id)} );

    }


    // grandezza db
    public long grandezzaDb() {
        SQLiteDatabase db = this.getWritableDatabase();
        long dim = db.getMaximumSize();
        return dim;
    }

}
