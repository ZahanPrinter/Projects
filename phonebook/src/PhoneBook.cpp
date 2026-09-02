#include "PhoneBook.h"
#include <sstream>
#include <fstream>
#include <iostream>

using namespace std;

//Insert Contact into HashTable, Trie and BST
bool PhoneBook::addContact(const string& name, const string& phone, const string& email, const string& address) {
    if(hashTable.find(phone)) return false;

    auto contact = make_shared<Contact>(name, phone, email, address);

    hashTable.insert(contact);
    trie.insert(contact);
    bst.insert(contact);

    return true;
}

//Remove COntact from HashTable, BST and Trie
bool PhoneBook::removeContact(const string& phone) {
    auto contact = hashTable.find(phone);
    if(!contact) return false;

    string name = contact->name;

    hashTable.remove(phone);
    trie.remove(name, phone);
    bst.remove(name, phone);
    return true;
}

//Finds Contact using phone number and updates details in BST and Trie accordingly
bool PhoneBook::updateContact(const string& phone, const string& newName, const string& newEmail, const string& newAddress) {
    auto contact = hashTable.find(phone);

    if(!contact) return false;

    if(newName != contact->name) {
        trie.remove(contact->name, phone);
        bst.remove(contact->name, phone);

        contact->name = newName;
        contact->email = newEmail;
        contact->address = newAddress;

        trie.insert(contact);
        bst.insert(contact);
    }
    else {
        contact->email = newEmail;
        contact->address = newAddress;
    }
    return true;
}

//Returns Pointer to location of number in table
shared_ptr<Contact> PhoneBook::findByPhone(const string& phone) const {
    return hashTable.find(phone);
}

//Returns vecotr of all contacts begining with a prefix
vector<shared_ptr<Contact>> PhoneBook::findByPrefix(const string& prefix) const {
    return trie.searchPrefix(prefix);
}

//Returns sorted vector of all contacts
vector<shared_ptr<Contact>> PhoneBook::listAllSorted() const {
    return bst.inOrderAll();
}

//Returns vector of all names between high and low
vector<shared_ptr<Contact>> PhoneBook::listRange(const string& low, const string& high) const {
    return bst.rangeQuery(low, high);
}

//Saves Contacts to comma seperated values file
bool PhoneBook::saveToFile(const string& path) const {
    ofstream file(path);

    if(!file.is_open()) return false;

    for(auto& contact : bst.inOrderAll()) {
        file<<contact->toCSV()<<"\n";
    }
    return true;
}

//Gets contacts from comma seperated values file
bool PhoneBook::loadFromFile(const string& path) {
    ifstream file(path);
    if(!file.is_open()) return false;

    string line;

    while(getline(file, line)) {
        if(line.empty()) continue;
        stringstream ss(line);
        string name, phone, email, address;
        getline(ss, name, ',');
        getline(ss, phone, ',');
        getline(ss, email, ',');
        getline(ss, address, ',');
        addContact(name, phone, email, address);
    }
    return true;
}