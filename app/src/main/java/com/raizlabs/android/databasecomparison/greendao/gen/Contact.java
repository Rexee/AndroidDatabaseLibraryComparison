package com.raizlabs.android.databasecomparison.greendao.gen;

import com.raizlabs.android.databasecomparison.greendao.gen.DaoSession;
import com.raizlabs.android.databasecomparison.interfaces.IContact;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "CONTACT".
 */
public class Contact implements IContact<AddressBook> {

    private Long id;
    private String name;
    private String email;
    private long addressbookId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ContactDao myDao;

    private AddressBook addressBook;
    private Long addressBook__resolvedKey;


    public Contact() {
    }

    public Contact(Long id) {
        this.id = id;
    }

    public Contact(Long id, String name, String email, long addressbookId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.addressbookId = addressbookId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getContactDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public AddressBook getAddressBookField() {
        return getAddressBook();
    }

    public long getAddressbookId() {
        return addressbookId;
    }

    public void setAddressbookId(long addressbookId) {
        this.addressbookId = addressbookId;
    }

    /** To-one relationship, resolved on first access. */
    public AddressBook getAddressBook() {
        long __key = this.addressbookId;
        if (addressBook__resolvedKey == null || !addressBook__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AddressBookDao targetDao = daoSession.getAddressBookDao();
            AddressBook addressBookNew = targetDao.load(__key);
            synchronized (this) {
                addressBook = addressBookNew;
            	addressBook__resolvedKey = __key;
            }
        }
        return addressBook;
    }

    public void setAddressBook(AddressBook addressBook) {
        if (addressBook == null) {
            throw new DaoException("To-one property 'addressbookId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.addressBook = addressBook;
            addressbookId = addressBook.getId();
            addressBook__resolvedKey = addressbookId;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    @Override
    public void saveAll() {

    }
}
