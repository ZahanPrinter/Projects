#include <iostream>
#include <limits>
#include "include\PhoneBook.h"

using namespace std;

const string DATA_STREAM = "contacts.csv";

void printMenu() {
    std::cout << "\n===== Phone Book (Hash Table + Trie + BST) =====\n"
              << "1. Add contact\n"
              << "2. Look up by phone number       [Hash Table  - O(1) avg]\n"
              << "3. Search by name prefix         [Trie        - O(L+k)]\n"
              << "4. List all contacts (sorted)    [BST inorder - O(n)]\n"
              << "5. List contacts in a name range [BST range   - O(log n + k)]\n"
              << "6. Update contact\n"
              << "7. Delete contact\n"
              << "8. Save to file\n"
              << "9. Exit\n"
              << "Choose an option: ";
}

void printContact(const shared_ptr<Contact>& c) {
    cout<<" "<<c->name<<" | "<<c->phone;
    if(!c->email.empty()) cout<<" | "<<c->email;
    if(!c->address.empty()) cout<<" | "<<c->address;
    cout<<"\n";
}

void printContacts(const vector<shared_ptr<Contact>>& contacts) {
    if(contacts.empty()) {
        cout<<"There are no Contacts."<<endl;
        return;
    }
    for(auto& c : contacts) printContact(c);
}

string readLine(const string& prompt) {
    cout<<prompt;
    string line;
    getline(cin, line);
    return line;
}

int main() {
    PhoneBook book;
    if(book.loadFromFile(DATA_STREAM)) {
        cout<<"Loaded "<<book.contactCount()<<" contact(s) from "<<DATA_STREAM<<endl;
    }

    while(true) {
        printMenu();
        string choiceString;
        getline(cin,choiceString);
        int choice = 0;
        try {
            stoi(choiceString);
        }
        catch(...) {
            cout<<"Please enter a number between 1-9.";
            continue;
        }

        switch(choice) {
            case 1: {
                string name = readLine("Name: ");
                string phone = readLine("Phone: ");
                string email = readLine("Email(Optional): ");
                string address = readLine("Address(Optional): ");
                if(book.addContact(name, phone, email, address)){
                    cout<<"Contact Added."<<endl;
                }
                else {
                    cout<<"Contact Already exists."<<endl;
                }
                break;
            }
            case 2: {
                string phone = readLine("Enter Phone number to Lookup: ");
                auto contact = book.findByPhone(phone);
                if(contact) printContact(contact);
                else cout<<phone<<" Not found."<<endl;
            }
            case 3: {
                string prefix = readLine("Name starts with: ");
                printContacts(book.findByPrefix(prefix));
                break;
            }
            case 4: {
                printContacts(book.listAllSorted());
                break;
            }
            case 5: {
                string low = readLine("From Name: ");
                string high = readLine("To Name: ");
                printContacts(book.listRange(low, high));
                break;
            }
            case 6: {
                string phone = readLine("Phone Number of contact to update: ");
                if(!book.findByPhone(phone)){
                    cout<<phone<<" not found."<<endl;
                    break;
                }
                string name = readLine("New Name: ");
                string email = readLine("New email(Optional): ");
                string address = readLine("New address(Optional): ");
                book.updateContact(phone, name, email, address);
                cout<<"Contact Updated."<<endl;
                break;
            }
            case 7: {
                string phone = readLine("Phone number to delete: ");
                if(book.findByPhone(phone)) book.removeContact(phone);
                else cout<<"Not found."<<endl;
            }
            case 8: {
                if(book.saveToFile(DATA_STREAM)) cout<<"Saved to "<<DATA_STREAM<<endl;
                else cout<<"Faild to save."<<endl;
                break;
            }
            case 9: {
                book.saveToFile(DATA_STREAM);
                cout<<"Saved, Goodbye."<<endl;
                break;
            }
            default :
                cout<<"Enter number 1-9."<<endl;
        }
    }
}