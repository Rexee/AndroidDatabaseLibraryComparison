# AndroidDatabaseLibraryComparison
A test between a few of the popular libraries running a speed test on how fast they load and save data.

## Benchmark Description

There are two benchmarks.  The Simple trial uses a flat schema for an address book so each row is composed of name, address, city, state, and phone columns.  
Simple model:
```java
public class SimpleAddressItem{
  String name;
  String address;
  String city;
  String state;
  long phone;
}
```
Complex model:
```java
public class AddressBook{
    Long id;
    String name;
    String author;
    Collection<AddressItem> addresses;
    Collection<Contact> contacts;
}
public class AddressItem extends SimpleAddressItem {
    private AddressBook addressBook;
}
public class Contact{
    String name;
    String email;
    AddressBook addressBook;
}
```


## Results

These are the results for the Simple trial:


And these are the results for the Complex trial:
