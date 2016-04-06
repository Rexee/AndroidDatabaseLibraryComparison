package com.raizlabs.android.databasecomparison.realm;


import com.raizlabs.android.databasecomparison.Generator;
import com.raizlabs.android.databasecomparison.MainActivity;
import com.raizlabs.android.databasecomparison.Verificator;
import com.raizlabs.android.databasecomparison.events.LogTestDataEvent;

import java.util.Collection;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmResults;


public class RealmTester {
    public static final String FRAMEWORK_NAME = "Realm";

    public static void testAddressBooks() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.clear(AddressItem.class);
                realm.clear(AddressBook.class);
                realm.clear(Contact.class);
            }
        });

        final Collection<AddressBook> addressBooks = Generator.createAddressBooks(AddressBook.class, Contact.class, AddressItem.class, MainActivity.COMPLEX_LOOP_COUNT, true);

        long startTime = System.currentTimeMillis();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(addressBooks);
            }
        });
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));
        realm.close();
    }

    public static void testAddressItems() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.clear(SimpleAddressItem.class);
            }
        });

        final Collection<SimpleAddressItem> addresses = Generator.getAddresses(SimpleAddressItem.class, MainActivity.SIMPLE_LOOP_COUNT);

        long startTime = System.currentTimeMillis();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(addresses);
            }
        });
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.SAVE_TIME));
        realm.close();
    }

    public static void testAddressBooksRead() {
        Realm realm = Realm.getDefaultInstance();

        long startTime = System.currentTimeMillis();
        RealmResults<AddressBook> addressBooks = realm.where(AddressBook.class).findAll();
        Verificator.verify(FRAMEWORK_NAME, addressBooks);
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));
        realm.close();
    }

    public static void testAddressItemsRead() {
        Realm realm = Realm.getDefaultInstance();

        long startTime = System.currentTimeMillis();
        RealmResults<SimpleAddressItem> addressItems = realm.where(SimpleAddressItem.class).findAll();
        Verificator.verifySimple(FRAMEWORK_NAME, addressItems);
        EventBus.getDefault().post(new LogTestDataEvent(startTime, System.currentTimeMillis(), FRAMEWORK_NAME, MainActivity.LOAD_TIME));
        realm.close();
    }
}
