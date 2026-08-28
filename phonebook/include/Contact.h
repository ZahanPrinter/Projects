#pragma once
#include <string>
#include <sstream>

using namespace std;

//Single Phonebook entry
//Storing as a struct
struct Contact {
    string name;
    string phone;
    string email;
    string address;

    Contact() = default; //Default Constructor

    Contact(string n, string p, string e ="", string a = ""):name(move(n)), phone(move(p)), email(move(e)), address(move(a)) {}

    //Convert to CSV
    string toCSV() const {
        ostringstream oss;
        oss<<name<<','<<phone<<','<<email<<','<<address;
        return oss.str();
    }
};