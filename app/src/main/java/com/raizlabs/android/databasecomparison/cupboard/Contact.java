package com.raizlabs.android.databasecomparison.cupboard;

import com.raizlabs.android.databasecomparison.interfaces.IContact;

/**
 * Description:
 */
public class Contact implements IContact<AddressBook> {

    public  Long _id;

    private String name;

    private String email;

    public AddressBook addressBook;

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
        return addressBook;
    }

    @Override
    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    @Override
    public void saveAll() {
//        super.save();
    }
}