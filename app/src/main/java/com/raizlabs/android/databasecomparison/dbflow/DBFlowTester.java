package com.raizlabs.android.databasecomparison.dbflow;

import com.raizlabs.android.databasecomparison.Generator;
import com.raizlabs.android.databasecomparison.MainActivity;
import com.raizlabs.android.databasecomparison.Saver;
import com.raizlabs.android.databasecomparison.Verificator;
import com.raizlabs.android.databasecomparison.events.LogTestDataEvent;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.Collection;

import de.greenrobot.event.EventBus;

/**
 * Description:
 */
public class DBFlowTester {
    public static final String FRAMEWORK_NAME = "DBFlow";

    public static void testAddressBooks() {
        com.raizlabs.android.dbflow.sql.language.Delete.tables(AddressItem.class, Contact.class, AddressBook.class);

        final Collection<AddressBook> addressBooks = Generator.createAddressBooks(AddressBook.class, Contact.class, AddressItem.class, MainActivity.COMPLEX_LOOP_COUNT, false);

        long startTime = System.currentTimeMillis();
        TransactionManager.transact(DBFlowDatabase.NAME, new Runnable() {
            @Override
            public void run() {
                Saver.saveAll(addressBooks);
            }
        });
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));
    }

    public static void testAddressItems() {
        com.raizlabs.android.dbflow.sql.language.Delete.table(SimpleAddressItem.class);

        final Collection<SimpleAddressItem> dbFlowModels = Generator.getAddresses(SimpleAddressItem.class, MainActivity.SIMPLE_LOOP_COUNT);

        long startTime = System.currentTimeMillis();
        TransactionManager.transact(DBFlowDatabase.NAME, new Runnable() {
            @Override
            public void run() {
                Saver.saveAll(dbFlowModels);
            }
        });
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));
    }

    public static void testAddressBooksRead() {
        long startTime = System.currentTimeMillis();
        Collection<AddressBook> addressBooks = new Select().from(AddressBook.class).queryList();
        Verificator.verify(FRAMEWORK_NAME, addressBooks);
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));
    }

    public static void testAddressItemsRead() {
        long startTime = System.currentTimeMillis();
        Collection<SimpleAddressItem> addressItems = new Select().from(SimpleAddressItem.class).queryList();
        Verificator.verifySimple(FRAMEWORK_NAME, addressItems);
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));
    }
}
