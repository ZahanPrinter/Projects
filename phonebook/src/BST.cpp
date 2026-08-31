#include "include\BST.h"
#include <cctype>
#include <algorithm>

using namespace std;

BST::BST() : root(nullptr) {} //Initilize empty node in BST

//Convert to Lowercase
string BST::normalize(const string& s) {
    string out = s;
    transform(out.begin(), out.end(), out.begin(), [](unsigned char c) { return tolower(c); });
    return out;
}

//Inserts Contact in BST
void BST::insertNode(unique_ptr<BSTNode>& node, const shared_ptr<Contact>& contact) {
    string key = normalize(contact->name);

    //If no node exists, make one(i.e point of insertion) else keep moving left or right depending on key to find where to add node
    if(!node) {
        node = make_unique<BSTNode>();
        node->key = key;
        node->contactsHere.push_back(contact);
    }
    else if(key < node->key) {
        insertNode(node->left, contact);
    }
    else if(key > node->key) {
        insertNode(node->right, contact);
    }
    else {
        node->contactsHere.push_back(contact); //If already exists appends contact to the node's vector
    }
}

//Used to call insertNode function
void BST::insert(const shared_ptr<Contact>& contact) {
    insertNode(root, contact);
}

//Collects all nodes left to root to right
void BST::inOrderCollect(BSTNode *node, vector<shared_ptr<Contact>>& results) const {
    if(!node) return;
    inOrderCollect(node->left.get(), results);
    for(auto& c : node->contactsHere) results.push_back(c);
    inOrderCollect(node->right.get(), results);
}

//used to call inOrderCollect and return vector of all
vector<shared_ptr<Contact>> BST::inOrderAll() const {
    vector<shared_ptr<Contact>> results;
    inOrderCollect(root.get(), results);
    return results;
}

//Collect names falling between low and high
void BST::rangeCollect(BSTNode* node, const string& low, const string& high, vector<shared_ptr<Contact>>& results) const {
    if(!node) return;

    if(node->key > low) {
        rangeCollect(node->left.get(), low, high, results);
    }
    if (node->key >= low && node->key <= high) {
        for(auto& c: node->contactsHere) results.push_back(c);
    }
    if(node->key < high) {
        rangeCollect(node->right.get(), low, high, results);
    }
}

//Call rangeCollect and return vector of all in range
vector<shared_ptr<Contact>> BST::rangeQuery(const string& lowName, const string& highName) const {
    vector<shared_ptr<Contact>> results;
    rangeCollect(root.get(), normalize(lowName), normalize(highName), results);
    return results;
}

//Recursively searches and removes a node with matching name and number
bool BST::removeFrom(unique_ptr<BSTNode>& node, const string& name, const string& phone) {
    if(!node) return false; //Doesn't exist
    string key = normalize(name);

    //Find the node
    if(key < node->key) {
        return removeFrom(node->left, name, phone);
    }
    if(key > node->key) {
        return removeFrom(node->right, name, phone);
    }

    //When matching node found, creates reference to node's contact vector
    auto& contacts = node->contactsHere;
    //Lambda Function to find exact ccontact based number
    auto it = find_if(contacts.begin(), contacts.end(), [&] (const shared_ptr<Contact>& c) { return c->phone == phone; });
    if(it == contacts.end()) return false; //No such contact, exit
    contacts.erase(it); //Remove Contact

    if(!contacts.empty()) return true; //If other contacts share this node, dont delete
    
    //If not then delete
    if(!node->left && !node->right) {
        node.reset();
    }
    else if(!node->left) { //If child node to right, it moves to its place
        node = move(node->right);
    }
    else if(!node->right) { //If child node to its left, move to its place
        node =move(node->left);
    }
    //Node has 2 children, finds smallest node by going right then left
    else {
        BSTNode* successor = node->right.get();
        while(successor->left) successor = successor->left.get();

        //Overwrites the current nodes key & data with Successor key and data
        node->key = successor->key;
        node->contactsHere = successor->contactsHere;

        removeFrom(node->right, successor->key, successor->contactsHere.front()->phone); //Recursively Delete successor node from right subtree
    }
    return true;
}

//Used to call removeFrom Function
bool BST::remove(const string& name, const string& phone) {
    return removeFrom(root, name, phone);
}