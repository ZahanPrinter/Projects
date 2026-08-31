#include "include\BST.h"
#include <cctype>
#include <algorithm>

using namespace std;

BST::BST() : root(nullptr) {}

string BST::normalize(const string& s) {
    string out = s;
    transform(out.begin(), out.end(), out.begin(), [](unsigned char c) { return tolower(c); });
    return out;
}

void BST::insertNode(unique_ptr<BSTNode>& node, const shared_ptr<Contact>& contact) {
    string key = normalize(contact->name);

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
        node->contactsHere.push_back(contact);
    }
}

void BST::insert(const shared_ptr<Contact>& contact) {
    insertNode(root, contact);
}

void BST::inOrderCollect(BSTNode *node, vector<shared_ptr<Contact>>& results) const {
    if(!node) return;
    inOrderCollect(node->left.get(), results);
    for(auto& c : node->contactsHere) results.push_back(c);
    inOrderCollect(node->right.get(), results);
}

vector<shared_ptr<Contact>> BST::inOrderAll() const {
    vector<shared_ptr<Contact>> results;
    inOrderCollect(root.get(), results);
    return results;
}

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

vector<shared_ptr<Contact>> BST::rangeQuery(const string& lowName, const string& highName) const {
    vector<shared_ptr<Contact>> results;
    rangeCollect(root.get(), normalize(lowName), normalize(highName), results);
    return results;
}

bool BST::removeFrom(unique_ptr<BSTNode>& node, const string& name, const string& phone) {
    if(!node) return false;
    string key = normalize(name);

    if(key < node->key) {
        return removeFrom(node->left, name, phone);
    }
    if(key > node->key) {
        return removeFrom(node->right, name, phone);
    }

    auto& contacts = node->contactsHere;
    auto it = find_if(contacts.begin(), contacts.end(), [&] (const shared_ptr<Contact>& c) { return c->phone == phone; });
    if(it == contacts.end()) return false;
    contacts.erase(it);

    if(!contacts.empty()) return true;

    if(!node->left && !node->right) {
        node.reset();
    }
    else if(!node->left) {
        node = move(node->right);
    }
    else if(!node->right) {
        node =move(node->left);
    }
    else {
        BSTNode* successor = node->right.get();
        while(successor->left) successor = successor->left.get();

        node->key = successor->key;
        node->contactsHere = successor->contactsHere;

        unique_ptr<BSTNode>* slot = &node->right;
        while((*slot)->left) slot = &(*slot)->left;
        *slot = move(((*slot)->left));
    }
    return true;
}

bool BST::remove(const string& name, const string& phone) {
    return removeFrom(root, name, phone);
}