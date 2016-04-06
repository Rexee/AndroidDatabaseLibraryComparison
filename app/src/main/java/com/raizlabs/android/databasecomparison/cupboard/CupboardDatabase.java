package com.raizlabs.android.databasecomparison.cupboard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nl.qbusict.cupboard.CupboardBuilder;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;
import static nl.qbusict.cupboard.CupboardFactory.setCupboard;

public class CupboardDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME    = CupboardDatabase.class.getName() + ".db";
    private static final int    DATABASE_VERSION = 1;

    static {
        setCupboard(new CupboardBuilder().useAnnotations().build());
        cupboard().register(AddressBook.class);
        cupboard().register(AddressItem.class);
        cupboard().register(Contact.class);
        cupboard().register(SimpleAddressItem.class);
    }

    public CupboardDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        cupboard().withDatabase(db).upgradeTables();
    }
}
