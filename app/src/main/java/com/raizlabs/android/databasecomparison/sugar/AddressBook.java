package com.raizlabs.android.databasecomparison.sugar;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.raizlabs.android.databasecomparison.interfaces.IAddressBook;

import java.util.Collection;

/**
 * Description:
 */
public class AddressBook extends SugarRecord implements IAddressBook<AddressItem, Contact> {

    private String name;

    private String author;

    @Ignore
    Collection<AddressItem> addresses;

    @Ignore
    Collection<Contact> contacts;

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
        if(addresses == null) {
            addresses = AddressItem.find(AddressItem.class, "ADDRESS_BOOK = ?", String.valueOf(getId()));
        }
        return addresses;
    }

    @Override
    public Collection<Contact> getContacts() {
        if(contacts == null) {
            contacts = Contact.find(Contact.class, "ADDRESS_BOOK = ?", String.valueOf(getId()));
        }
        return contacts;
    }

    @Override
    public void setContacts(Collection<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public void saveAll() {
        super.save();
        for(AddressItem addressItem : addresses) {
            addressItem.saveAll();
        }
        for(Contact contact: contacts) {
            contact.saveAll();
        }
    }
}
