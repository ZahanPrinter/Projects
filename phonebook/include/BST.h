#pragma once
#include <string>
#include <vector>
#include <memory>
#include "Contact.h"

using namespace std;

class BST{
    public:
        BST();
        void insert(const shared_ptr<Contact>& contact); //Add new contact using shared pointer
        vector<shared_ptr<Contact>> inOrderAll() const; //Returns Sorted List of all contacts
        vector<shared_ptr<Contact>> rangeQuery(const string& lowName, const string& highName) const; //Searches and returns all names that fall between lowName and highName
        bool remove(const string& name, const string& phone); //Deletes Contact based on name and phone

    private:
        //Struct represent a single node in the tree
        struct BSTNode {
            string key; //Lookup String
            vector<shared_ptr<Contact>> contactsHere; //Vector of Contacts, with similar names, different numbers
            unique_ptr<BSTNode> left; //Points to node to the left
            unique_ptr<BSTNode> right; //Point to node to the right
        };

        unique_ptr<BSTNode> root; //Root node, starts here

        static string normalize(const string& s); //Convert to Lowercase
        //All following are recursive functions
        void insertNode(unique_ptr<BSTNode>& node, const shared_ptr<Contact>& contact); //Helps find the correct spot to insert a new node/contact
        void inOrderCollect(BSTNode* node, vector<shared_ptr<Contact>>& results) const; //Checkes nodes left, root, and right to gather alphabetically
        void rangeCollect(BSTNode* node, const string& low, const string& high, vector<shared_ptr<Contact>>& results) const; //Searches only the branches of the tree within the specified range
        bool removeFrom(unique_ptr<BSTNode>& node, const string& name, const string& phone); //Find and delete contact
};