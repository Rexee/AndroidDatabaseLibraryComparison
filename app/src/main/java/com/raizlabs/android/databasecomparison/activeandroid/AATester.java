package com.raizlabs.android.databasecomparison.activeandroid;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.raizlabs.android.databasecomparison.Generator;
import com.raizlabs.android.databasecomparison.MainActivity;
import com.raizlabs.android.databasecomparison.Saver;
import com.raizlabs.android.databasecomparison.Verificator;
import com.raizlabs.android.databasecomparison.events.LogTestDataEvent;

import java.util.Collection;

import de.greenrobot.event.EventBus;

/**
 * Description:
 */
public class AATester {
    public static final String FRAMEWORK_NAME = "ActiveAndroid";

    public static void testAddressBooks() {
        new Delete().from(AddressItem.class).execute();
        new Delete().from(Contact.class).execute();
        new Delete().from(AddressBook.class).execute();

        Collection<AddressBook> addressBooks = Generator.createAddressBooks(AddressBook.class, Contact.class, AddressItem.class, MainActivity.COMPLEX_LOOP_COUNT, true);

        long startTime = System.currentTimeMillis();
        ActiveAndroid.beginTransaction();
        try {
            Saver.saveAll(addressBooks);
            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));

    }

    public static void testAddressItems() {
        new Delete().from(SimpleAddressItem.class).execute();

        final Collection<SimpleAddressItem> activeAndroidModels = Generator.getAddresses(SimpleAddressItem.class, MainActivity.SIMPLE_LOOP_COUNT);

        long startTime = System.currentTimeMillis();
        ActiveAndroid.beginTransaction();
        try {
            Saver.saveAll(activeAndroidModels);
            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }

        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));

    }

    public static void testAddressBooksRead() {
        long startTime = System.currentTimeMillis();
        Collection<AddressBook> addressBooks = new Select().from(AddressBook.class).execute();
        Verificator.verify(FRAMEWORK_NAME, addressBooks);
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));

    }

    public static void testAddressItemsRead() {
        long startTime = System.currentTimeMillis();
        Collection<SimpleAddressItem> activeAndroidModelLoad = new Select().from(SimpleAddressItem.class).execute();
        Verificator.verifySimple(FRAMEWORK_NAME, activeAndroidModelLoad);
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));

    }
}
