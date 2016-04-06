package com.raizlabs.android.databasecomparison.sugar;

import com.orm.SugarTransactionHelper;
import com.orm.SugarTransactionHelper.Callback;
import com.raizlabs.android.databasecomparison.Generator;
import com.raizlabs.android.databasecomparison.Verificator;
import com.raizlabs.android.databasecomparison.MainActivity;
import com.raizlabs.android.databasecomparison.Saver;
import com.raizlabs.android.databasecomparison.events.LogTestDataEvent;

import java.util.Collection;

import de.greenrobot.event.EventBus;

/**
 * Description:
 */
public class SugarTester {
    public static final String FRAMEWORK_NAME = "Sugar";

    public static void testAddressBooks() {
        AddressItem.deleteAll(AddressItem.class);
        AddressBook.deleteAll(AddressBook.class);
        Contact.deleteAll(Contact.class);

        final Collection<AddressBook> addressBooks = Generator.createAddressBooks(AddressBook.class, Contact.class, AddressItem.class, MainActivity.COMPLEX_LOOP_COUNT, true);

        long startTime = System.currentTimeMillis();
        SugarTransactionHelper.doInTransaction(new Callback() {
            @Override
            public void manipulateInTransaction() {
                Saver.saveAll(addressBooks);
            }
        });

        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));
    }

    public static void testAddressItems() {
        SimpleAddressItem.deleteAll(SimpleAddressItem.class);

        Collection<SimpleAddressItem> sugarModelList = Generator.getAddresses(SimpleAddressItem.class, MainActivity.SIMPLE_LOOP_COUNT);

        long startTime = System.currentTimeMillis();
        SimpleAddressItem.saveInTx(sugarModelList);
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));
    }

    public static void testAddressBooksRead() {
        long startTime = System.currentTimeMillis();
        Collection<AddressBook> addressBooks = AddressBook.listAll(AddressBook.class);
        Verificator.verify(FRAMEWORK_NAME, addressBooks);
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));
    }

    public static void testAddressItemsRead() {
        long startTime = System.currentTimeMillis();
        Collection<SimpleAddressItem> sugarModelList = SimpleAddressItem.listAll(SimpleAddressItem.class);
        Verificator.verifySimple(FRAMEWORK_NAME, sugarModelList);
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));
    }
}
