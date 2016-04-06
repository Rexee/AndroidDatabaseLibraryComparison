package com.raizlabs.android.databasecomparison.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME    = SqlHelper.class.getName() + ".db";
    private static final int    DATABASE_VERSION = 1;
    String addIdstr = "";

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SqlHelper(Context context, String name, int ver, boolean addId) {
        super(context, name, null, ver);
        if (addId) {
            this.addIdstr = "id integer primary key autoincrement, ";
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String db1 = "drop table IF EXISTS " + SimpleAddressItem.NAME;
        String db2 = "drop table IF EXISTS " + AddressBook.NAME;
        String db3 = "drop table IF EXISTS " + AddressItem.NAME;
        String db4 = "drop table IF EXISTS " + Contact.NAME;

        db.execSQL(db1);
        db.execSQL(db2);
        db.execSQL(db3);
        db.execSQL(db4);

        db1 = "create table " + SimpleAddressItem.NAME + " (addId, 'address' TEXT, 'city' TEXT, 'name' TEXT, 'phone' INTEGER, 'state' TEXT)";
        db3 = "create table " + AddressBook.NAME + " (id integer primary key autoincrement, 'author' TEXT, 'name' TEXT)";
        db2 = "create table " + AddressItem.NAME + " (addId, 'addressBook' INTEGER, 'address' TEXT, 'city' TEXT, 'name' TEXT, 'phone' INTEGER, 'state' TEXT)";
        db4 = "create table " + Contact.NAME + " (addId, 'addressBook' INTEGER, 'email' TEXT, 'name' TEXT)";

        db1 = db1.replace("addId, ", addIdstr);
        db3 = db3.replace("addId, ", addIdstr);
        db2 = db2.replace("addId, ", addIdstr);
        db4 = db4.replace("addId, ", addIdstr);

        db.execSQL(db1);
        db.execSQL(db2);
        db.execSQL(db3);
        db.execSQL(db4);

        db1 = "create INDEX book_addr_idx ON " + AddressItem.NAME + " (addressBook)";
        db2 = "create INDEX book_cont_idx ON " + Contact.NAME + " (addressBook)";
        db.execSQL(db1);
        db.execSQL(db2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
