package com.raizlabs.android.databasecomparison.dbflow;

import com.raizlabs.android.databasecomparison.MainActivity;
import com.raizlabs.android.databasecomparison.interfaces.IAddressBook;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Collection;

/**
 * Description:
 */
//3.x
@ModelContainer
@Table(database = DBFlowDatabase.class, cachingEnabled = true, cacheSize = MainActivity.COMPLEX_LOOP_COUNT)
public class AddressBook extends BaseModel implements IAddressBook<AddressItem, Contact> {

//2.x
//@ModelContainer
//@Table(tableName = "AddressBook", databaseName = DBFlowDatabase.NAME)
//public class AddressBook extends BaseCacheableModel implements IAddressBook<AddressItem, Contact> {

//    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column(name = "name") String name;

    @Column(name = "author") String author;

    Collection<AddressItem> addresses;

    Collection<Contact> contacts;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAddresses(Collection<AddressItem> addresses) {
        this.addresses = addresses;
    }

    //3.x
//    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE, Method.LOAD}, variableName = "addresses")
    public Collection<AddressItem> getAddresses() {
        if (addresses == null) {
            addresses = new Select().from(AddressItem.class).where(AddressItem_Table.addressBook_id.eq(id)).queryList();
        }
        return addresses;
    }

//    2.x
//    public Collection<AddressItem> getAddresses() {
//        if (addresses == null) {
//            addresses = new Select().from(AddressItem.class).where(Condition.column(AddressItem$Table.ADDRESSBOOK_ADDRESSBOOK).is(id)).queryList();
//        }
//        return addresses;
//    }

    //3.x
//    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE, Method.LOAD}, variableName = "contacts")
    public Collection<Contact> getContacts() {
        if (contacts == null) {
            contacts = new Select().from(Contact.class).where(Contact_Table.addressBook_id.eq(id)).queryList();
        }
        return contacts;
    }

    //2.x
//    public Collection<Contact> getContacts() {
//        if (contacts == null) {
//            contacts = new Select().from(Contact.class).where(Condition.column(Contact$Table.ADDRESSBOOK_ADDRESSBOOK).is(id)).queryList();
//        }
//        return contacts;
//    }

    public void setContacts(Collection<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public void saveAll() {
        super.insert();
        for (AddressItem addressItem : addresses) {
            addressItem.setAddressBook(this);
            addressItem.saveAll();
        }
        for (Contact contact : contacts) {
            contact.setAddressBook(this);
            contact.saveAll();
        }
    }

    //2.x
//    @Override
//    public int getCacheSize() {
//        return MainActivity.COMPLEX_LOOP_COUNT;
//    }

}