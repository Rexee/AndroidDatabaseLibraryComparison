package com.raizlabs.android.databasecomparison.interfaces;

import java.util.Collection;

/**
 * Description: interface for address book objects
 */
public interface IAddressBook<AddressItem extends IAddressItem, Contact extends IContact> extends ISaveable {

    void setName(String name);

    String getName();

    void setAuthor(String author);

    void setAddresses(Collection<AddressItem> addresses);

    Collection<AddressItem> getAddresses();

    Collection<Contact> getContacts();

    void setContacts(Collection<Contact> contacts);
}
