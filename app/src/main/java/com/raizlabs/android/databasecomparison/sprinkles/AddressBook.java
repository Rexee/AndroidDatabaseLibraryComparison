package com.raizlabs.android.databasecomparison.sprinkles;

import com.raizlabs.android.databasecomparison.interfaces.IAddressBook;

import java.util.Collection;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.Query;
import se.emilsjolander.sprinkles.Transaction;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Description:
 */
@Table("AddressBook")
public class AddressBook extends Model implements IAddressBook<AddressItem, Contact>{

    @Column("id")
    @AutoIncrement
    @Key
    private long id;

    @Column("name")
    private String name;

    @Column("author")
    private String author;

    Collection<AddressItem> addresses;

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
        if (addresses == null) {
            addresses = Query.many(AddressItem.class, "select * from AddressItem where addressBook=?", String.valueOf(id)).get().asList();
        }
        return addresses;
    }

    @Override
    public Collection<Contact> getContacts() {
        if (contacts == null) {
            contacts = Query.many(Contact.class, "select * from Contact where addressBook=?", String.valueOf(id)).get().asList();
        }
        return contacts;
    }

    public long getId() {
        return id;
    }

    @Override
    public void setContacts(Collection<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public void saveAll() {

    }

    public void saveAll(Transaction transaction) {
        super.save(transaction);
        for (AddressItem addressItem : addresses) {
            addressItem.setAddressBook(this);
            addressItem.save(transaction);
        }
        for (Contact contact : contacts) {
            contact.setAddressBook(this);
            contact.save(transaction);
        }
    }
}
