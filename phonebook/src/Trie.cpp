#include "include\Trie.h"
#include <algorithm>
#include <string>
#include <cctype>

using namespace std;

Trie::Trie() : root(make_unique<TrieNode>()) {} //Initialize contructor with root node

//Used to convert all into lowercase
string normalize(const string& s) {
    string out = s;
    transform(out.begin(), out.end(), out.begin(), [](unsigned char c) { return tolower(c); }); //Iterates through each letter to make lowercase using lambda function
    return out;
}

//Used to Insert Values into the Trie
void Trie::insert(const shared_ptr<Contact>& contact) {
    string name = normalize(contact->name); //Normalize name
    TrieNode* current = root.get(); //Get root node

    //Loop through each character in name
    for(char c: name) {
        if(current->children.find(c) == current->children.end()){ //Check if currrent node corresponding character
            current->children[c] = make_unique<TrieNode>(); //If not make one
        }
        current = current->children[c].get(); //Move pointer down to child node matching char c
    }
    current->contactsHere.push_back(contact); //Adds contact to the vector at final node
}

//Used to Navigate down the tree to find a prefix
Trie::TrieNode* Trie::walkTo(const string& prefix) const {
    TrieNode* current = root.get();
    //Loop through each char in perfix
    for(char c : prefix) {
        auto it = current->children.find(c); //Searches for char c in the map
        if(it == current->children.end()) {
            return nullptr; //returns null if it cant find
        }
        current = it->second.get(); //When found, returns pointer to the child node found in the map
    }
    return current;
}

//Recursively traverses branches and subranches of the trie to gather matching contact
void Trie::collectAll(TrieNode* node, vector<shared_ptr<Contact>>& results) const {
    if(!node) return; //if current node is null returns

    //Loop through contacts at this node
    for(auto& contact : node->contactsHere) {
        results.push_back(contact); //Adds contacts to results vector
    }
    //Use of structured binding to loop through each key-value pair (character & child node) in children map
    for(auto& [ch, child] : node->children) {
        collectAll(child.get(), results); //Calls itself to reach sub branches
    }
}

//Return Vector of names that begin with a prefix
vector<shared_ptr<Contact>> Trie::searchPrefix(const string& prefix) const {
    vector<shared_ptr<Contact>> results; //Create vector for results
    TrieNode* node = walkTo(normalize(prefix)); //Normalize and walk to ending node in the trie
    collectAll(node, results); //Collect all nodes below that node
    return results;
}

//Remove contact matching specific name and number
bool Trie::remove(const string& name, const string& phone) {
    TrieNode* node = walkTo(normalize(name)); //Navigates to node representing end of name
    if(!node) return false; //If the name node doesnt exist returns falsee
    
    auto& contacts = node->contactsHere; //Creates reference to the vector of contacts at this node
    auto it = find_if(contacts.begin(), contacts.end(), [&](const shared_ptr<Contact>& c) { return c->phone == phone;}); //uses find_if with lambda function to find contact with matching phone number
    
    if(it==contacts.end()) return false; // if no such contacts, return false
    contacts.erase(it); //Remove contact at end of node if found
    return true; //Successfully deleted
}