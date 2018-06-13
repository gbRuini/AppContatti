package com.mobdev.appcontatti;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobdev.appcontatti.Model.Contatto;

import java.util.ArrayList;

/**
 * Created by gabriele on 07/06/18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Contatto> mDataset;
    private Context context;


    public MyAdapter(ArrayList<Contatto> myDataset) {

        mDataset = myDataset;

    }

    //Crea una nuova view
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        ViewHolder vh = new ViewHolder(view);

        return vh;
    }


    //Sostituisce il contenuto della view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.setText("" + mDataset.get(position).getName()  /* " " + mDataset.get(position).getSurname()*/ );


    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View v = null;

        public ViewHolder(final View v) {
            super(v);
            this.v = v;

            //passo il contesto in cui mi trovo
            context = v.getContext();

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(MainActivity.TAG, "Ho premuto un contatto : " + getAdapterPosition() );


                    // tramite putExtra passo i dati del contesto in ViewContact
                    Intent n = new Intent(context, ViewContact.class);
                    n.putExtra("contatto",  mDataset.get(getAdapterPosition()));
                    context.startActivity(n);
                }
            });
        }



        public void setText(String text) {
            TextView tView = (TextView) v.findViewById(R.id.nome);
            tView.setText(text);
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
