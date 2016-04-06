package com.raizlabs.android.databasecomparison.interfaces;

/**
 * Description: interface for contact objects in address book
 */
public interface IContact<AddressBook extends IAddressBook> extends ISaveable {

    String getName();

    void setName(String name);

    String getEmail();

    void setEmail(String email);

    AddressBook getAddressBookField();

    void setAddressBook(AddressBook addressBook);
}
