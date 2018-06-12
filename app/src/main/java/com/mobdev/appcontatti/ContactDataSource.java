package com.mobdev.appcontatti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mobdev.appcontatti.Model.Contatto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriele on 07/06/18.
 */

public class ContactDataSource {

    private static final String TAG = "Contact DataSource";

    //campi del database
    private SQLiteDatabase database;
    private ContactOpenHelper dbHelper;
    private String[] allColumns = { ContactOpenHelper.ID_COL, ContactOpenHelper.NOME_COL, ContactOpenHelper.COGNOME_COL, ContactOpenHelper.INDIRIZZO_COL, ContactOpenHelper.NUMERO_COL, ContactOpenHelper.EMAIL_COL, /* ContactOpenHelper.TIPO_COL, ContactOpenHelper.IMMAGINE_COL*/  };

    public ContactDataSource(Context context) {
        dbHelper = new ContactOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Metodo per aggiungere un nuovo record al DB partendo da un oggetto Contatto
     *
     *
     * @return
     */
    public Contatto createContact(Contatto contatto) {

        Log.d(MainActivity.TAG, "Creating Log Descritor ...");


        //Oggetto usato per definire le colonne e usare i valori per creare un nuovo record nel db
        ContentValues values = new ContentValues();
        values.put(ContactOpenHelper.NOME_COL, contatto.getName());
        values.put(ContactOpenHelper.COGNOME_COL, contatto.getSurname());
        values.put(ContactOpenHelper.NUMERO_COL, contatto.getPhoneNumber());
        values.put(ContactOpenHelper.INDIRIZZO_COL, contatto.getAddress());
        values.put(ContactOpenHelper.EMAIL_COL, contatto.getEmail());
      //  values.put(ContactOpenHelper.TIPO_COL, contatto.getTypeNumber());
       // values.put(ContactOpenHelper.IMMAGINE_COL, contatto.getImageContact());

        //Inserisco un nuovo record nel DB ed ottengo un nuovo ID come risultato
        long insertId = database.insert(ContactOpenHelper.TABLE_NAME, null, values);

        if (insertId == -1)
            return null;
        else {
            Log.d(MainActivity.TAG, "New Log record created: " + insertId);

            // To show how to query with for a specific record according to the id
            Cursor cursor = database.query(ContactOpenHelper.TABLE_NAME, allColumns, ContactOpenHelper.ID_COL + " = " + insertId, null, null, null, null);
            cursor.moveToFirst();
            return cursorToContatto(cursor);
        }
    }


        // cancellare tutti i contatti

    public void deleteAllContacts() {
        database.delete(ContactOpenHelper.TABLE_NAME, null, null);
    }


    // eliminare un contatto tramite ID
    public void deleteContact(Contatto contatto) {
        long id = contatto.getId();
        database.delete(ContactOpenHelper.TABLE_NAME, ContactOpenHelper.ID_COL + " = " + id, null);
    }


    /**
     * Metodo che restituisce l'intera Lista di contatti salvati nel DB
     *
     * @return
     */
    public List<Contatto> getAllContacts() {

        //  Log.d(MainActivity.TAG, "getAllLogsDescriptor ...");

        List<Contatto> logList = new ArrayList<Contatto>();

        Cursor cursor = database.query(ContactOpenHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Contatto contatto = cursorToContatto(cursor);
            logList.add(contatto);
            cursor.moveToNext();
        }
        // Mi assicuro di chiudere il cursore
        cursor.close();
        return logList;
    }

    /**
     * Metodo che restituisce l'intera Lista di contatti salvati nel DB ordinati per ID descrescente
     *
     * @return
     */
    public List<Contatto> getAllContactsOrderByIdDesc() {

        // Log.d(MainActivity.TAG, "getAllLogsDescriptor ...");

        List<Contatto> logList = new ArrayList<Contatto>();

        Cursor cursor = database.query(ContactOpenHelper.TABLE_NAME,
                allColumns, null, null, null, null, ContactOpenHelper.ID_COL + " DESC");
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Contatto logDescr = cursorToContatto(cursor);
            logList.add(logDescr);
            cursor.moveToNext();
        }
        // Chiudo cursore
        cursor.close();
        return logList;
    }


    public int getContactCount() {

        int logCount = 0;

        String query = "SELECT COUNT(*) FROM " + ContactOpenHelper.TABLE_NAME;

        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            logCount = cursor.getInt(0);
            cursor.moveToNext();
        }

        cursor.close();


        return logCount;
    }



    /**
     * Utility method to start from a Cursor Object and obtain back a LogDescriptor
     *
     * @param cursor
     * @return
     */
    private Contatto cursorToContatto(Cursor cursor) {
        Contatto contatto = new Contatto();

        contatto.setId(cursor.getInt(0));
        contatto.setName(cursor.getString(1));
        contatto.setSurname(cursor.getString(2));
        contatto.setPhoneNumber(cursor.getString(3));
        contatto.setAddress(cursor.getString(4));
        contatto.setEmail(cursor.getString(5));
      //  contatto.setTypeNumber(cursor.getString(6));
      //  contatto.setImageContact(cursor.getString(7));

        return contatto;
    }
}
