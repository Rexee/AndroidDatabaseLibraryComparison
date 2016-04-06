package com.raizlabs.android.databasecomparison.cupboard;

/**
 * Description:
 */
public class AddressItem extends SimpleAddressItem {

    private AddressBook addressBook;

    @Override
    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }
}
