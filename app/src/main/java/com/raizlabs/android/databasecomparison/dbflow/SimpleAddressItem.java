package com.raizlabs.android.databasecomparison.dbflow;

import com.raizlabs.android.databasecomparison.MainActivity;
import com.raizlabs.android.databasecomparison.interfaces.IAddressItem;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Description:
 */
//3.x
@Table(database = DBFlowDatabase.class, cachingEnabled = true, cacheSize = MainActivity.COMPLEX_LOOP_COUNT)
public class SimpleAddressItem extends BaseModel implements IAddressItem<AddressBook> {

//2.x
//@Table(databaseName = DBFlowDatabase.NAME)
//public class SimpleAddressItem extends BaseCacheableModel implements IAddressItem<AddressBook> {

    @PrimaryKey(autoincrement = true)
    @Column
    long id;

    @Column(name = "name")
    String name;

    @Column(name = "address")
    String address;

    @Column(name = "city")
    String city;

    @Column(name = "state")
    String state;

    @Column(name = "phone")
    long phone;

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
        super.insert();
    }

//2.x
//    @Override
//    public int getCacheSize() {
//        return MainActivity.SIMPLE_LOOP_COUNT;
//    }

    @Override
    public long getPhone() {
        return phone;
    }

    @Override
    public String getAddress() {
        return address;
    }
}
