package com.raizlabs.android.databasecomparison.cupboard;

import com.raizlabs.android.databasecomparison.interfaces.IAddressBook;

import java.util.Collection;

import nl.qbusict.cupboard.DatabaseCompartment;
import nl.qbusict.cupboard.annotation.Ignore;

/**
 * Description:
 */
public class AddressBook implements IAddressBook<AddressItem, Contact> {

    public Long _id;
    private String name;
    private String author;

    @Ignore Collection<AddressItem> addresses;
    @Ignore Collection<Contact>     contacts;

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

    public void saveAll(DatabaseCompartment dbc) {
        dbc.put(this);
        for (Contact c : contacts) {
            dbc.put(c);
        }
        for (AddressItem a : addresses) {
            dbc.put(a);
        }
    }

    @Override
    public void saveAll() {
    }

    public void loadAll(DatabaseCompartment dbc) {
        addresses = dbc.query(AddressItem.class).withSelection("addressBook = ?", String.valueOf(_id)).list();
        contacts = dbc.query(Contact.class).withSelection("addressBook = ?", String.valueOf(_id)).list();
    }
}
