package com.raizlabs.android.databasecomparison.realm;

import com.raizlabs.android.databasecomparison.interfaces.IAddressItem;

import io.realm.RealmObject;

/**
 * Description:
 */
public class SimpleAddressItem extends RealmObject implements IAddressItem<AddressBook> {

    private String name;

    private String address;

    private String city;

    private String state;

    private long phone;

    public SimpleAddressItem() {
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public long getPhone() {
        return phone;
    }

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
    public void saveAll() {

    }
}
