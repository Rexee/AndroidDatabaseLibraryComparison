package com.raizlabs.android.databasecomparison.interfaces;

/**
 * Description: interface for address book items objects in address book
 */
public interface IAddressItem<AddressBook extends IAddressBook> extends ISaveable {

    void setName(String name);

    void setAddress(String address);

    void setCity(String city);

    void setState(String state);

    void setPhone(long phone);

    void setAddressBook(AddressBook addressBook);

    String getName();
    long getPhone();
    String getAddress();
}
