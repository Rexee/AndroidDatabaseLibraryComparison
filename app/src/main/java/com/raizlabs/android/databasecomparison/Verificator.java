package com.raizlabs.android.databasecomparison;


import android.util.Log;

import com.raizlabs.android.databasecomparison.interfaces.IAddressBook;
import com.raizlabs.android.databasecomparison.interfaces.IAddressItem;
import com.raizlabs.android.databasecomparison.interfaces.IContact;

import java.util.Collection;

/**
 * Description:
 */
public class Verificator {
    public static final String TAG = "Verify. ";

    /**
     * Trigger a load of all inner data to truly test speed of loading
     *
     * @param addressBooks
     */
    public static void verify(String framework, Collection<? extends IAddressBook> addressBooks) {
        if (addressBooks == null || addressBooks.size() != MainActivity.COMPLEX_LOOP_COUNT) {
            Log.e(TAG + framework, "AddrBook not match");
            return;
        }

        String name;
        for (IAddressBook addressBook : addressBooks) {
            name = addressBook.getName();
            if (name == null || !name.substring(0, 8).equals("AddrBook")) {
                Log.e(TAG + framework, "AddrBook not match");
                return;
            }

            Collection<IAddressItem> addresses = addressBook.getAddresses();
            if (addresses == null || addresses.size() != MainActivity.COMPLEX_LOOP_COUNT) {
                Log.e(TAG + framework, "Addr not match");
                return;
            }
            for (IAddressItem type : addresses) {
                name = type.getName();
                if (name == null || !name.substring(0, 4).equals("Addr")) {
                    Log.e(TAG + framework, "Addr not match");
                    return;
                }
            }

            Collection<IContact> contacts = addressBook.getContacts();
            if (contacts == null || contacts.size() != MainActivity.COMPLEX_LOOP_COUNT) {
                Log.e(TAG + framework, "Contact not match");
                return;
            }
            for (IContact contact : contacts) {
                if (contact.getAddressBookField() == null) {
                    Log.e(TAG + framework, "Contact-AddrBook not match");
                    return;
                }
                name = contact.getName();
                if (name == null || !name.substring(0, 7).equals("Contact")) {
                    Log.e(TAG + framework, "Contact not match");
                    return;
                }
            }
        }
        Log.e(TAG + framework, "complex verify: OK");
    }

    public static void verifySimple(String framework, Collection<? extends IAddressItem> addressItems) {
        if (addressItems == null || addressItems.size() != MainActivity.SIMPLE_LOOP_COUNT) {
            Log.e(TAG + framework, "Simple addr not match");
            return;
        }

        String name;
        long phone;
        String addr;
        for (IAddressItem address : addressItems) {
            name = address.getName();
            if (name == null || !name.substring(0, 4).equals("Addr")) {
                Log.e(TAG + framework, "Addr not match");
                return;
            }
            if (address.getPhone() != Generator.PHONE) {
                Log.e(TAG + framework, "Addr not match");
                return;
            }
            if (!address.getAddress().equals(Generator.ADDR)) {
                Log.e(TAG + framework, "Addr not match");
                return;
            }
        }
        Log.e(TAG + framework, "simple verify: OK");
    }
}
