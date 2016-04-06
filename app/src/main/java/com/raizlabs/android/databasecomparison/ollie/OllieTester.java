package com.raizlabs.android.databasecomparison.ollie;

import android.database.sqlite.SQLiteDatabase;

import com.raizlabs.android.databasecomparison.Generator;
import com.raizlabs.android.databasecomparison.MainActivity;
import com.raizlabs.android.databasecomparison.Saver;
import com.raizlabs.android.databasecomparison.Verificator;
import com.raizlabs.android.databasecomparison.events.LogTestDataEvent;

import java.util.Collection;

import de.greenrobot.event.EventBus;
import ollie.Ollie;
import ollie.query.Delete;
import ollie.query.Select;

/**
 * Created by Tjones on 8/16/15.
 */
public class OllieTester {
    public static final String FRAMEWORK_NAME = "Ollie";

    public static void testAddressBooks() {
        Delete.from(AddressItem.class).execute();
        Delete.from(Contact.class).execute();
        Delete.from(AddressBook.class).execute();

        Collection<AddressBook> addressBooks = Generator.createAddressBooks(AddressBook.class, Contact.class, AddressItem.class, MainActivity.COMPLEX_LOOP_COUNT, true);

        long startTime = System.currentTimeMillis();
        SQLiteDatabase db = Ollie.getDatabase();
        db.beginTransaction();
        try {
            Saver.saveAll(addressBooks);
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }

        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));

    }

    public static void testAddressItems() {
        Delete.from(SimpleAddressItem.class).execute();

        final Collection<SimpleAddressItem> ollieModels = Generator.getAddresses(SimpleAddressItem.class, MainActivity.SIMPLE_LOOP_COUNT);

        long startTime = System.currentTimeMillis();
        SQLiteDatabase db = Ollie.getDatabase();
        db.beginTransaction();
        try {
            Saver.saveAll(ollieModels);
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));

    }

    public static void testAddressBooksRead() {
        long startTime = System.currentTimeMillis();
        Collection<AddressBook> addressBooks = Select.from(AddressBook.class).fetch();
        Verificator.verify(FRAMEWORK_NAME, addressBooks);
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));

    }

    public static void testAddressItemsRead() {
        long startTime = System.currentTimeMillis();
        Collection<SimpleAddressItem> activeAndroidModelLoad = Select.from(SimpleAddressItem.class).fetch();
        Verificator.verifySimple(FRAMEWORK_NAME, activeAndroidModelLoad);
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));

    }
}
