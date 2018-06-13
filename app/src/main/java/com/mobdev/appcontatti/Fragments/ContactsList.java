package com.mobdev.appcontatti.Fragments;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mobdev.appcontatti.AddActivity;
import com.mobdev.appcontatti.DbHelper;
import com.mobdev.appcontatti.Model.Contatto;
import com.mobdev.appcontatti.MyAdapter;
import com.mobdev.appcontatti.R;
import com.mobdev.appcontatti.ViewContact;

import java.util.ArrayList;

/**
 * Created by gabriele on 06/06/18.
 */

/*

    Fragment che mostra la lista di tutti i contatti, viene caricato nella Main Activity e rappresenta la schermata iniziale dell'app

 */

public class ContactsList extends Fragment  {

    private ImageButton addButton = null;
    private RecyclerView mRecyclerView = null;
    private LinearLayoutManager mLayoutManager = null;
    private MyAdapter myAdapter = null;
    private Context mContext;

    public ContactsList() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        mContext= this.getContext();

        mRecyclerView  = (RecyclerView)view.findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        mLayoutManager  = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.scrollToPosition(0);

        mRecyclerView.setLayoutManager(mLayoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        setupContactList();

        /*contactsList = ContactManager.getInstance(mContext).getContactList();*/
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(getActivity(), ViewContact.class);
                startActivity(n);
            }
        });



        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        //floating button per aggiungere nuovo contatto

        addButton = (ImageButton) view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //creo intent per passare da un activity all'altra
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);


            }
        });

        return view;

    }


    private void setupContactList() {

        // creo un array list di contatti
        ArrayList<Contatto> contactsList = new ArrayList<>();

        DbHelper dbHelper = new DbHelper(getActivity());
        Cursor cursor = dbHelper.getAllContacts();

        if(!cursor.moveToNext()) {
            Toast.makeText(getActivity(), "Non ci sono contatti!", Toast.LENGTH_LONG).show();
        }

        //itero attraverso le righe del db
        while (cursor.moveToNext()) {
            contactsList.add(new Contatto(
                    cursor.getString(1),    // nome
                    cursor.getString(2),    // cognome
                    cursor.getString(3),    // numero
                    cursor.getString(4),   // email
                    cursor.getString(5)    // indirizzo
            ));
        }

        myAdapter = new MyAdapter(contactsList);
        mRecyclerView.setAdapter(myAdapter);

    }


}
