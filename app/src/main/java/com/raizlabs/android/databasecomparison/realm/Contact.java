package com.raizlabs.android.databasecomparison.realm;

import com.raizlabs.android.databasecomparison.interfaces.IContact;

import io.realm.RealmObject;

/**
 * Description:
 */
public class Contact extends RealmObject implements IContact<AddressBook> {

    private String name;

    private String email;

    private AddressBook addressBook;

    public Contact() {
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public AddressBook getAddressBookField() {
        return getAddressBook();
    }

    @Override
    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    @Override
    public void saveAll() {

    }
}
