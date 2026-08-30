#pragma once
#include <string>
#include <unordered_map>
#include <vector>
#include <memory>
#include "Contact.h"

using namespace std;

class Trie{
    public:
        Trie(); //Constructor
        void insert(const shared_ptr<Contact>& contact); //Insert values into the trie
        vector<shared_ptr<Contact>> searchPrefix(const string& prefix) const; //Constant function that returns all names starting with a given prefix
        bool remove(const string& name, const string& number); //Returns true if deleting a node was successful
    private: 
        struct TrieNode {
            unordered_map<char, unique_ptr<TrieNode>> children;
            vector<shared_ptr<Contact>> contactsHere;
        };

        unique_ptr<TrieNode> root; //Starting Point of the Trie
        static string normalize(const string& s); //Convert to Lowercase
        TrieNode* walkTo(const string& prefix) const; //Navigates Down tree to find prefix
        void collectAll(TrieNode* node, vector<shared_ptr<Contact>>& results) const; //Recursive function, traverses sub branches of a node to gather matching contact
};