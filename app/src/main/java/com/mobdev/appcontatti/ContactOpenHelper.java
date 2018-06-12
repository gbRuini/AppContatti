package com.mobdev.appcontatti;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by gabriele on 07/06/18.
 */

public class ContactOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "contatti.db";

    public static final String TABLE_NAME = "contatti";

    public static final String ID_COL = "id";
    public static final String NOME_COL = "nome";
    public static final String COGNOME_COL = "cognome";
    public static final String INDIRIZZO_COL = "indirizzo";
    public static final String NUMERO_COL = "numero";
  //  public static final String TIPO_COL = "tipo";
    public static final String EMAIL_COL = "email";
 //   public static final String IMMAGINE_COL = "immagine";


    private static final String DATABASE_TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOME_COL + " VARCHAR(50), " +
                    COGNOME_COL + " VARCHAR(50), " +
                    INDIRIZZO_COL + " VARCHAR(150), " +
                    NUMERO_COL + " INTEGER, " +
                 //   TIPO_COL + " VARCHAR(50),"+
                    EMAIL_COL + " VARCHAR(80)"+
                    ");";

    public ContactOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // Log.d("OpenHelper Constructor !");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       // Log.d(MainActivity.TAG,"LogOpenHelper onCreate !");
        db.execSQL(DATABASE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // Log.d(MainActivity.TAG,"LogOpenHelper onUpgrade !");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(DATABASE_TABLE_CREATE);
    }
}
