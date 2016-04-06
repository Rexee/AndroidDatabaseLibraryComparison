package com.raizlabs.android.databasecomparison.realm;

import com.raizlabs.android.databasecomparison.interfaces.IAddressBook;

import java.util.Collection;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Description:
 */
public class AddressBook extends RealmObject implements IAddressBook<AddressItem, Contact> {

    private String name;

    private String author;

    private RealmList<AddressItem> addresses;

    private RealmList<Contact> contacts;

    public AddressBook() {
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAddresses(RealmList<AddressItem> addresses) {
        this.addresses = addresses;
    }

    public void setContacts(RealmList<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public void setAddresses(Collection<AddressItem> addresses) {
        this.addresses = new RealmList<>();
        for (AddressItem addr : addresses) {
            this.addresses.add(addr);
        }
    }

    @Override
    public void setContacts(Collection<Contact> contacts) {
        this.contacts = new RealmList<>();
        for (Contact contact : contacts) {
            this.contacts.add(contact);
        }
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
    public void saveAll() {

    }
}
