package com.raizlabs.android.databasecomparison.cupboard;

import com.raizlabs.android.databasecomparison.interfaces.IAddressItem;

/**
 * Description:
 */
public class SimpleAddressItem implements IAddressItem<AddressBook> {

    public  Long _id;

    private String name;

    private String address;

    private String city;

    private String state;

    private long phone;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public void setPhone(long phone) {
        this.phone = phone;
    }

    @Override
    public void setAddressBook(AddressBook addressBook) {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void saveAll() {
    }

    @Override
    public long getPhone() {
        return phone;
    }

    @Override
    public String getAddress() {
        return address;
    }
}
