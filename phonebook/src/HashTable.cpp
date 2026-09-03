#include "HashTable.h"
#include <iostream>
#include <vector>

using namespace std;

//Constructor, initializes Hash table with i think 16 buckets and sets numElements at 0
HashTable::HashTable(size_t bucketCount) : numElements(0) {
    buckets.resize(bucketCount); //Allocates memory
}

//Defining hashFuction, takes phone and returns array index, const so that it doesnt modify the table state
//Initialize hash and prime multiplier, loop through each character in the string, multiplies by the prime, and adds ASCII value of the char
//Returns modulo to compress large hash to valid index for buckets vector;
size_t HashTable::hashFunction(const string& key) const {
    size_t hash = 0;
    const size_t prime = 31;
    for(char c: key) {
        hash = hash * prime + static_cast<unsigned char>(c);
    }
    return hash % buckets.size();
}

//When we reach close to max capacity, we double table size
void HashTable::resizeAndRehash() {
    vector<unique_ptr<Node>> oldBuckets = move(buckets); //temporary vector for old contacts
    buckets.clear(); //Clear table
    buckets.resize(oldBuckets.size()*2); //Double table size
    numElements = 0; //Restart Counter
    //iterate thtough the vector and add to table
    for (auto& head : oldBuckets) {
        Node* current = head.get();
        while(current) {
            insert(current->contact);
            current = current->next.get();
        }
    }
}

void HashTable::insert(const shared_ptr<Contact>& contact) {
    //When inserting call resize and rehash if too much load
    double loadFactor = static_cast<double>(numElements + 1) / buckets.size();
    if(loadFactor > 0.75) {
        resizeAndRehash();
    }

    size_t index = hashFunction(contact->phone); //hash the phone number to determine what bucket it goes into

    //Check if already exists and update
    Node* current = buckets[index].get();
    while (current) {
    if (current->contact->phone == contact->phone) {
        current->contact = contact;  // update existing entry
        return;
    }
    current = current->next.get();   // only advance if this one didn't match
}

    //Initiate new Node and assign contact to it
    auto newNode = make_unique<Node>();
    newNode->contact = contact;
    newNode->next = move(buckets[index]); //Point new node's next pointer to what used to be first item in bucket
    buckets[index] = move(newNode); //Place new node in front
    numElements++; //Increment total elements
}

//Find Contact using Phone Number
shared_ptr<Contact> HashTable::find(const string& phone) const {
    size_t index = hashFunction(phone);
    Node* current = buckets[index].get();
    while(current) {
        if(current->contact->phone == phone) {
            return current->contact;
        }
        current = current->next.get();
    }
    return nullptr;
}

//Reomves Contact based on Phone Number
bool HashTable::remove(const string& phone) {
    size_t index = hashFunction(phone);
    Node* current = buckets[index].get();
    Node* prev = nullptr;

    while(current) {
        if(current->contact->phone == phone) {
            //If item is in the middle or end of list, safely unlink current by shifting ownership of current->next to prev->next
            if(prev){
                prev->next = move(current->next);
            }
            //If item is first, update bucket head to point directly to second node
            else {
                buckets[index] = move(current->next);
            }
            numElements--;
            return true;
        }
        //Advance both trackers in front
        prev = current;
        current = current->next.get();
    }
    return false;
}