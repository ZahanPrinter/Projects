This was a JDBC Connection project I made a long time back during my school days as a fun Java/SQL Project.
The point of this was to teach myself the connection between an SQL database and a Java frontend.
It contains 20+ CRUD Operations, spread out across 4 Data Access Objects(DAO).
When running this on personal systems, Ensure the following:
    1. You have edited the datbase/DatabaseConnection.java file to your mysql server details
    2. Ensure you have a schema by the name of school_erp on the same server
    3. If you wish to test the system, i have included a AI generated Script to generate 10,000 fake records, run it in the sql workbench   after creating the database and running main.java once (This will initialize the tables).