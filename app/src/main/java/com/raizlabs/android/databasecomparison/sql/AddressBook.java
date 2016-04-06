package com.raizlabs.android.databasecomparison.sql;

import com.raizlabs.android.databasecomparison.interfaces.IAddressBook;

import java.util.Collection;

/**
 * Description:
 */
public class AddressBook implements IAddressBook<AddressItem, Contact> {
    public static final String NAME = AddressBook.class.getSimpleName();

    long id;
    public String name;
    public String author;

    Collection<AddressItem> addresses;
    Collection<Contact>     contacts;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public void setAddresses(Collection<AddressItem> addresses) {
        this.addresses = addresses;
    }

    @Override
    public Collection<AddressItem> getAddresses() {
        return addresses;
    }

    @Override
    public Collection<Contact> getContacts() {
        return contacts;
    }

    @Override
    public void setContacts(Collection<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public void saveAll() {
    }

}
