package com.mobdev.appcontatti;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.mobdev.appcontatti.Model.Contatto;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by gabriele on 07/06/18.
 */

public class ContactManager {

    private Context context = null;

    /*
     * L'istanza è statica in modo da poterla condividere con tutte le instanze della classe.
     * Inoltre è private così è accessibile solo all'interno della classe
     */
    private static ContactManager instance = null;

    //creo "contenitore" per i vari contatti che andrò a inserire
    private ArrayList<Contatto> contactList = null;

    /*
     * Constructor
     */
    private ContactManager(Context context){
      //  Log.d(MainActivity.TAG,"Number Manager Created !");
        this.context = context;
		/*
		 * Provo a vedere se esiste già una lista di contatti, se esiste la carico nell'ArrayList
		 */
        try {
            ContactDataSource datasource = new ContactDataSource(context);
            datasource.open();

            this.contactList = (ArrayList<Contatto>) datasource.getAllContactsOrderByIdDesc();

            datasource.close();

          //  Log.d(MainActivity.TAG,"Log File available ! List size: " + this.logList.size());
        } catch(Exception e) {

            //Se non c'è un file esistente crea un arrayList vuoto
            this.contactList = new ArrayList<Contatto>();
           // Log.e(MainActivity.TAG,"Error Reading Log List on File: " + e.getLocalizedMessage());
        }
    }

    public static ContactManager getInstance(Context context){
		/*
		 *  Il costruttore è chiamato solo se istanza statica è nulla, così viene chiamato solo la prima volta
		 *  che la getInstance() è invocata.
		 *  Tutte le altre volte viene ritornata la stessa istanza
		 *
		 */
        if(instance == null)
            instance = new ContactManager(context);
        return instance;
    }

    public void addContact(Contatto contatto){
        this.contactList.add(contatto);

        try {

            ContactDataSource datasource = new ContactDataSource(context);
            datasource.open();

            if(datasource.createContact(contatto) != null)
                Toast.makeText(context, "Nuovo Contatto Aggiunto Correttamente !", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "ERRORE, Aggiungi nuovo contatto !", Toast.LENGTH_LONG).show();

            datasource.close();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Saving Log List on File ...", Toast.LENGTH_LONG).show();
        }

    }

    public void addContactToHead(Contatto contatto){
        this.contactList.add(0,contatto);

        try {

            ContactDataSource datasource = new ContactDataSource(context);
            datasource.open();

            if(datasource.createContact(contatto) != null)
                Toast.makeText(context, "Nuovo Contatto Aggiunto Correttamente !", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "ERRORE!", Toast.LENGTH_LONG).show();

            datasource.close();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Saving Log List on File ...", Toast.LENGTH_LONG).show();
        }
    }

    public void removeContact(Contatto contatto){
        this.contactList.remove(contatto);

        try {

            ContactDataSource datasource = new ContactDataSource(context);
            datasource.open();

            datasource.deleteContact(contatto);

            datasource.close();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Saving Log List on File ...", Toast.LENGTH_LONG).show();
        }
    }

    public void removeContact(int position){
        this.removeContact(this.contactList.get(position));
    }

    public ArrayList<Contatto> getContactList(){
        return contactList;
    }



}
