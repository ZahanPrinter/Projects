#pragma once
#include <string>
#include <vector>
#include <memory>
#include "Contact.h"
#include "Trie.h"
#include "HashTable.h"
#include "BST.h"

using namespace std;

class PhoneBook{
    public:
        PhoneBook() = default;

        bool addContact(const string& name, const string& phone, const string& email = "", const string& address = ""); //Checks if contact exists already when adding
        bool removeContact(const string& phone); //Removes Contaact based on number
        bool updateContact(const string& phone, const string& newName, const string& newEmail, const string& newAddress); //Finds contact by number and updates other details

        shared_ptr<Contact> findByPhone(const string& phone) const; //Searched for contact based on phone number, returns shared ptr to contact
        vector<shared_ptr<Contact>> findByPrefix(const string& prefix) const; //Returns list of names starting with given prefix
        vector<shared_ptr<Contact>> listAllSorted() const; //Returns list of Contacts sorted alphabetically
        vector<shared_ptr<Contact>> listRange(const string& low, const string& high) const; //Returns list of names between low and high
        
        size_t contactCount() const {return hashTable.size();}; //Returns total no. of contacts

        bool saveToFile(const string& path) const; //Exports contacts to file, returns true if successful
        bool loadFromFile(const string& path); //Imports and populates data from a given path, returns true if successful
        
    private:
        HashTable hashTable;
        Trie trie;
        BST bst;
};