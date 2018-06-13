package com.mobdev.appcontatti.Model;

/**
 * Created by gabriele on 13/06/18.
 */

public class DbContract {
    private DbContract() {

    }

    public static final String DATABASE_NAME = "contacts_2.db";
    public static final String TABLE_NAME = "contacts_table";

    public static final String _ID = "id_";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_SURNAME = "Surname";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_NUMBER = "Mobile";
    //public static final String COLUMN_TYPE = "DEVICE_TYPE";
    public static final String COLUMN_EMAIL = "Email";
   // public static final String COLUMN_IMAGE = "PROFILE_IMAGE";
}
