package com.raizlabs.android.databasecomparison.sprinkles;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.raizlabs.android.databasecomparison.Generator;
import com.raizlabs.android.databasecomparison.MainActivity;
import com.raizlabs.android.databasecomparison.Verificator;
import com.raizlabs.android.databasecomparison.events.LogTestDataEvent;
import com.raizlabs.android.databasecomparison.sql.SqlHelper;

import java.util.Collection;

import de.greenrobot.event.EventBus;
import se.emilsjolander.sprinkles.Query;
import se.emilsjolander.sprinkles.Transaction;


public class SprinklesTester {
    public static final String FRAMEWORK_NAME = "Sprinkles";

    public static void testAddressItems(Context context) {
        SQLiteOpenHelper openHelper = new SqlHelper(context, "sprinkles.db", 1, true);
        openHelper.getWritableDatabase().delete("SimpleAddressItem", null, null);

        Collection<SimpleAddressItem> sprinkleModels = Generator.getAddresses(SimpleAddressItem.class, MainActivity.SIMPLE_LOOP_COUNT);
        long startTime = System.currentTimeMillis();

        Transaction transaction = new Transaction();
        try {
            for (SimpleAddressItem model : sprinkleModels) {
                model.save(transaction);
            }
            transaction.setSuccessful(true);
        } finally {
            transaction.finish();
        }

        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));

    }

    public static void testAddressBooks(Context context) {
        SQLiteOpenHelper openHelper = new SqlHelper(context, "sprinkles.db", 1, true);
        openHelper.getWritableDatabase().delete("AddressItem", null, null);
        openHelper.getWritableDatabase().delete("Contact", null, null);
        openHelper.getWritableDatabase().delete("AddressBook", null, null);

        final Collection<AddressBook> addressBooks = Generator.createAddressBooks(AddressBook.class, Contact.class, AddressItem.class, MainActivity.COMPLEX_LOOP_COUNT, false);

        long startTime = System.currentTimeMillis();
        Transaction transaction = new Transaction();
        try {
            for (AddressBook book : addressBooks) {
                book.saveAll(transaction);
            }
            transaction.setSuccessful(true);
        } finally {
            transaction.finish();
        }

        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));

    }

    public static void testAddressItemsRead(Context context) {
        long startTime = System.currentTimeMillis();
        Collection<SimpleAddressItem> sprinkleModels = Query.all(SimpleAddressItem.class).get().asList();
        Verificator.verifySimple(FRAMEWORK_NAME, sprinkleModels);
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));

    }

    public static void testAddressBooksRead(Context context) {
        long startTime = System.currentTimeMillis();
        Collection<AddressBook> addressBooks = Query.all(AddressBook.class).get().asList();
        Verificator.verify(FRAMEWORK_NAME, addressBooks);
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));

    }
}
