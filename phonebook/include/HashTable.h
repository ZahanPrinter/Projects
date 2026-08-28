#pragma once
#include <string>
#include <vector>
#include <memory>
#include "Contact.h"

using namespace std;

class HashTable {
    public: 
        explicit HashTable(size_t bucketCount = 16); //Prevents implicit conversion, It is a Constructor used to initialize a vector with default 16  buckets

        void insert(shared_ptr<Contact>& contact); //Used to insert new contact, hashes phone number to find target element, scan for duplicates and append the contact
        shared_ptr<Contact> find(const string& phone) const; //Hashes requested Phone Number, navigates to corresponding element index by traversing the linked list, returns shared ptr if found else null
        bool remove(string& phone); //Removes Number, Searches for phone number at designated element and unlinks the node from the chain, triggering unique_ptr to free memory
        size_t size() const {return numElements;} // Returns total elements
        size_t bucketCountValue() const { return buckets.size();} //Returns size of vector

    private:
        struct Node{ // Node Struct
            shared_ptr<Contact> contact;
            unique_ptr<Node> next;
        };

        vector<unique_ptr<Node>> buckets; //Main Array
        size_t numElements; //Total Contacts
        
        size_t hashFunction(const string& key) const; //Used to minimize index collisions, by looping through the chars of phone number string & multiplying cumulative sum by a constant factor and adding character values

        void resizeAndRehash(); //Safety mechanism for when load exceed 75%
};