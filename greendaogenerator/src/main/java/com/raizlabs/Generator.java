package com.raizlabs;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class Generator {

    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.raizlabs.android.databasecomparison.greendao.gen");

        addSimpleAddressItemEntity(schema);

        Entity addressBook = getAddressBookEntity(schema);

        addAddressItemEntity(addressBook, schema);
        addContactItemEntity(addressBook, schema);

        try {
            new DaoGenerator().generateAll(schema,
                    "../app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Entity addSimpleAddressItemEntity(Schema schema) {
        Entity entity = schema.addEntity("SimpleAddressItem");
        entity.addIdProperty();
        entity.addStringProperty("name");
        entity.addStringProperty("address");
        entity.addStringProperty("city");
        entity.addStringProperty("state");
        entity.addLongProperty("phone");
        return entity;
    }

    private static Entity addContactItemEntity(Entity addressBook, Schema schema) {
        Entity entity = schema.addEntity("Contact");
        entity.addIdProperty();
        entity.addStringProperty("name");
        entity.addStringProperty("email");
        Property addressbookId = entity.addLongProperty("addressbookId").notNull().getProperty();
        entity.addToOne(addressBook, addressbookId);

        ToMany addrList = addressBook.addToMany(entity, addressbookId);
        addrList.setName("contactList");

        return entity;
    }

    static Entity addAddressItemEntity(Entity addressBook, Schema schema) {
        Entity entity = schema.addEntity("AddressItem");
        entity.addIdProperty();
        entity.addStringProperty("name");
        entity.addStringProperty("address");
        entity.addStringProperty("city");
        entity.addStringProperty("state");
        entity.addLongProperty("phone");
        Property addressbookId = entity.addLongProperty("addressbookId").notNull().getProperty();
        entity.addToOne(addressBook, addressbookId);

        ToMany addrList = addressBook.addToMany(entity, addressbookId);
        addrList.setName("addressItemList");

        return entity;
    }

    static Entity getAddressBookEntity(Schema schema) {
        Entity addressBook = schema.addEntity("AddressBook");
        addressBook.addIdProperty();
        addressBook.addStringProperty("name");
        addressBook.addStringProperty("author");
        return addressBook;
    }
}
