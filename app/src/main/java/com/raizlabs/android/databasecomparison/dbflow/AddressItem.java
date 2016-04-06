package com.raizlabs.android.databasecomparison.dbflow;

import com.raizlabs.android.databasecomparison.interfaces.IAddressItem;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

/**
 * Description:
 */
@Table(database = DBFlowDatabase.class)//3.x
//@Table(databaseName = DBFlowDatabase.NAME)//2.x
public class AddressItem extends BaseModel implements IAddressItem<AddressBook> {

    //    @Column
    @PrimaryKey(autoincrement = true)
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
    public void saveAll() {
        super.insert();
    }

    //3.x
    @ForeignKey(saveForeignKeyModel = false)
    ForeignKeyContainer<AddressBook> addressBook;

    //2.x
//    @ForeignKey(
//            references = {@ForeignKeyReference(columnName = "addressBook", columnType = Long.class, foreignColumnName = "id")},
//            saveForeignKeyModel = false)
//    @Column
//    ForeignKeyContainer<AddressBook> addressBook;


    @Override
    public void setAddressBook(AddressBook addressBook) {
        //3.x
        this.addressBook = FlowManager.getContainerAdapter(AddressBook.class).toForeignKeyContainer(addressBook);

        //2.x
//        this.addressBook = new ForeignKeyContainer<>(AddressBook.class);
//        Map<String, Object> keys = new LinkedHashMap<>();
//        keys.put(AddressBook$Table.ID, addressBook.id);
//        this.addressBook.setData(keys);
    }

    @Override
    public String getName() {
        return name;
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
