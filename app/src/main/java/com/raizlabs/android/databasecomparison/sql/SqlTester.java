package com.raizlabs.android.databasecomparison.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.raizlabs.android.databasecomparison.Generator;
import com.raizlabs.android.databasecomparison.MainActivity;
import com.raizlabs.android.databasecomparison.Verificator;
import com.raizlabs.android.databasecomparison.events.LogTestDataEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Description:
 */
public class SqlTester {
    public static final String FRAMEWORK_NAME = "Raw SQL";

    public static void testAddressItems(Context context) {
        SqlHelper dbHelper = new SqlHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + SimpleAddressItem.NAME);
//        db.execSQL("PRAGMA synchronous=OFF");
        List<SimpleAddressItem> addresses = (List<SimpleAddressItem>) Generator.getAddresses(SimpleAddressItem.class, MainActivity.SIMPLE_LOOP_COUNT);
        long startTime = System.currentTimeMillis();

//        int maxRows = 1;
        String values = "VALUES(?1, ?2, ?3, ?4, ?5)";
//        int curValNum = 6;
//        for (int i = 1; i < maxRows; i++) {
//            values += String.format(",(?%d, ?%d, ?%d, ?%d, ?%d)", curValNum++, curValNum++, curValNum++, curValNum++, curValNum++);
//        }

        SQLiteStatement stmt = db.compileStatement("INSERT INTO " + SimpleAddressItem.NAME + " " + values);

        db.beginTransaction();

//        curValNum = 1;
//        int curRow = 1;
        for (SimpleAddressItem addr : addresses) {
            stmt.bindString(1, addr.address);
            stmt.bindString(2, addr.city);
            stmt.bindString(3, addr.name);
            stmt.bindLong(4, addr.phone);
            stmt.bindString(5, addr.state);
            stmt.executeInsert();
            stmt.clearBindings();

//            stmt.bindString(curValNum++, addr.address);
//            stmt.bindString(curValNum++, addr.city);
//            stmt.bindString(curValNum++, addr.name);
//            stmt.bindLong(curValNum++, addr.phone);
//            stmt.bindString(curValNum++, addr.state);
//            if (curRow++ == maxRows) {
//                stmt.executeInsert();
//                stmt.clearBindings();
//                curRow = 1;
//                curValNum = 1;
//            }
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));

        db.close();
        dbHelper.close();
    }

    public static void testAddressItemsRead(Context context) {
        SqlHelper dbHelper = new SqlHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long startTime = System.currentTimeMillis();

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s", SimpleAddressItem.NAME), null);

        SimpleAddressItem addr;
        Collection<SimpleAddressItem> addressItems = new ArrayList<>();

        cursor.moveToFirst();
        do {
            addr = new AddressItem();
            addr.address = cursor.getString(0);
            addr.city = cursor.getString(1);
            addr.name = cursor.getString(2);
            addr.phone = cursor.getLong(3);
            addr.state = cursor.getString(4);

            addressItems.add(addr);
        } while (cursor.moveToNext());

        cursor.close();

        Verificator.verifySimple(FRAMEWORK_NAME, addressItems);

        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));

        db.close();
        dbHelper.close();
    }

    public static void testAddressBooks(Context context) {
        SqlHelper dbHelper = new SqlHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + AddressBook.NAME);
        db.execSQL("DELETE FROM " + AddressItem.NAME);
        db.execSQL("DELETE FROM " + Contact.NAME);

        Collection<AddressBook> addressBooks = Generator.createAddressBooks(AddressBook.class, Contact.class, AddressItem.class, MainActivity.COMPLEX_LOOP_COUNT, true);
        long startTime = System.currentTimeMillis();

        SQLiteStatement stmtBook = db.compileStatement("INSERT INTO " + AddressBook.NAME + " ('name', 'author') VALUES(?1, ?2)");
        SQLiteStatement stmtAddr = db.compileStatement("INSERT INTO " + AddressItem.NAME + " ('addressBook', 'address', 'city', 'name', 'phone', 'state') VALUES(?1, ?2, ?3, ?4, ?5, ?6)");
        SQLiteStatement stmtCont = db.compileStatement("INSERT INTO " + Contact.NAME + " ('addressBook', 'email', 'name') VALUES(?1, ?2, ?3)");

        long id;

        try {
            db.beginTransaction();
            for (AddressBook book : addressBooks) {
                stmtBook.clearBindings();
                stmtBook.bindString(1, book.getName());
                stmtBook.bindString(2, book.author);
                id = stmtBook.executeInsert();
                book.id = id;

                for (AddressItem addr : book.addresses) {
                    stmtAddr.bindLong(1, id);
                    stmtAddr.bindString(2, addr.getAddress());
                    stmtAddr.bindString(3, addr.city);
                    stmtAddr.bindString(4, addr.getName());
                    stmtAddr.bindLong(5, addr.getPhone());
                    stmtAddr.bindString(6, addr.state);
                    stmtAddr.executeInsert();
                }

                for (Contact contact : book.contacts) {
                    stmtCont.bindLong(1, id);
                    stmtCont.bindString(2, contact.getEmail());
                    stmtCont.bindString(3, contact.getName());
                    stmtCont.executeInsert();
                }
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));

        db.close();
        dbHelper.close();
    }

    public static void testAddressBooksRead(Context context) {
        SqlHelper dbHelper = new SqlHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long startTime = System.currentTimeMillis();

        String queryAddr = "SELECT * FROM " + AddressItem.NAME + " WHERE addressBook = ?";
        String queryCont = "SELECT * FROM " + Contact.NAME + " WHERE addressBook = ?";

        AddressBook addressBook;
        AddressItem addressItem;
        Contact contact;
        String[] params = new String[1];

        Cursor cursorAddr;
        Cursor cursorCont;
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s", AddressBook.NAME), null);
        Collection<AddressBook> addressItems = new ArrayList<>(cursor.getCount());
        cursor.moveToFirst();
        do {
            addressBook = new AddressBook();
            addressBook.id = cursor.getLong(0);
            addressBook.author = cursor.getString(1);
            addressBook.name = cursor.getString(2);

            params[0] = String.valueOf(addressBook.id);

            cursorAddr = db.rawQuery(queryAddr, params);
            if (cursorAddr.moveToFirst()) {
                addressBook.addresses = new ArrayList<>(cursorAddr.getCount());
                do {
                    addressItem = new AddressItem();
                    addressItem.setAddressBook(addressBook);
                    addressItem.setAddress(cursorAddr.getString(1));
                    addressItem.setCity(cursorAddr.getString(2));
                    addressItem.setName(cursorAddr.getString(3));
                    addressItem.setPhone(cursorAddr.getLong(4));
                    addressItem.setState(cursorAddr.getString(5));
                    addressBook.addresses.add(addressItem);

                } while (cursorAddr.moveToNext());
            }

            cursorCont = db.rawQuery(queryCont, params);
            if (cursorCont.moveToFirst()) {
                addressBook.contacts = new ArrayList<>(cursorCont.getCount());
                do {
                    contact = new Contact();
                    contact.setAddressBook(addressBook);
                    contact.setEmail(cursorCont.getString(1));
                    contact.setName(cursorCont.getString(2));
                    addressBook.contacts.add(contact);

                } while (cursorCont.moveToNext());
            }

            addressItems.add(addressBook);

        } while (cursor.moveToNext());

        cursorAddr.close();
        cursorCont.close();
        cursor.close();

        Verificator.verify(FRAMEWORK_NAME, addressItems);

        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));

        db.close();
        dbHelper.close();
    }
}
