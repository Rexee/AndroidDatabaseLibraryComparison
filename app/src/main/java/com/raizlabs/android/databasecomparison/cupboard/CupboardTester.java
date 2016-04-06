package com.raizlabs.android.databasecomparison.cupboard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.raizlabs.android.databasecomparison.Generator;
import com.raizlabs.android.databasecomparison.MainActivity;
import com.raizlabs.android.databasecomparison.Verificator;
import com.raizlabs.android.databasecomparison.events.LogTestDataEvent;

import java.util.Collection;

import de.greenrobot.event.EventBus;
import nl.qbusict.cupboard.DatabaseCompartment;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Description:
 */
public class CupboardTester {
    public static final String FRAMEWORK_NAME = "Cupboard";

    public static void testAddressItems(Context context) {
        CupboardDatabase database = new CupboardDatabase(context);
        SQLiteDatabase db = database.getWritableDatabase();
        DatabaseCompartment dbc = cupboard().withDatabase(db);
        dbc.delete(SimpleAddressItem.class, null);

        Collection<SimpleAddressItem> addresses = Generator.getAddresses(SimpleAddressItem.class, MainActivity.SIMPLE_LOOP_COUNT);
        long startTime = System.currentTimeMillis();
        dbc.put(addresses);

        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));
        database.close();
    }

    public static void testAddressItemsRead(Context context) {
        CupboardDatabase database = new CupboardDatabase(context);
        SQLiteDatabase db = database.getWritableDatabase();
        DatabaseCompartment dbc = cupboard().withDatabase(db);

        long startTime = System.currentTimeMillis();
        Collection<SimpleAddressItem> addressItems = dbc.query(SimpleAddressItem.class).list();
        Verificator.verifySimple(FRAMEWORK_NAME, addressItems);
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));
        database.close();
    }

    public static void testAddressBooks(Context context) {
        CupboardDatabase database = new CupboardDatabase(context);
        SQLiteDatabase db = database.getWritableDatabase();
        DatabaseCompartment dbc = cupboard().withDatabase(db);
        dbc.delete(AddressBook.class, null);
        dbc.delete(Contact.class, null);
        dbc.delete(AddressItem.class, null);

        final Collection<AddressBook> addressBooks = Generator.createAddressBooks(AddressBook.class, Contact.class, AddressItem.class, MainActivity.COMPLEX_LOOP_COUNT, true);
        long startTime = System.currentTimeMillis();

        db.beginTransaction();
        try {
            for (AddressBook book : addressBooks) {
                book.saveAll(dbc);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));
        database.close();
    }

    public static void testAddressBooksRead(Context context) {
        CupboardDatabase database = new CupboardDatabase(context);
        SQLiteDatabase db = database.getWritableDatabase();
        DatabaseCompartment dbc = cupboard().withDatabase(db);

        long startTime = System.currentTimeMillis();
        Collection<AddressBook> addressBooks = dbc.query(AddressBook.class).query().list();
        for (AddressBook book : addressBooks) {
            book.loadAll(dbc);
        }
        Verificator.verify(FRAMEWORK_NAME, addressBooks);
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));
        database.close();
    }
}
