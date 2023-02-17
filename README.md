# Required setup:
* [MySQL Server 8.0.32](https://dev.mysql.com/downloads/windows/installer/8.0.html) (For local database)

# Development info
This application was developed using:
* Java 17 
* IntelliJ IDEA 2022.3.2 (Ultimate Edition), Build #IU-223.8617.56
* All maven dependencies were downloaded locally to `./localDependencies` for local build purpose


# Instructions
1. Install MySQL DB server
2. Create a localhost database with port 3306
3. Create database named stock_db `CREATE DATABASE stock_db`
4. after the setup, the MySQL server should accessible from  `mysql://localhost:3306/stock_db`
5. Change the DB login _**name**_ and _**password**_ in the yaml file

# RESTful API:
1. POST `/bo/stock/batchUpload` - Upload a bulk data set. You can find the sample data file at `./sample_data_file/dow_jones_index.data`
2. GET `/bo/stock/{symbol}` - Query for data by stock ticker (e.g. input: AA, would return 12 elements if the only data uploaded were the single data set above)
3. POST `/bo/stock/create` - Add a new record

You can find a postman template for testing in `./postman_templete/Stock_bo.postman_collection.json`

Note: postman can be buggy sometimes, for the _batchUpload_ API, you may need to check and uncheck the `Content-Type` in the header and re-select the upload file to get it work.

# Further improvement:
1. Avoid using root for DB management. It is better to have separate db_admin account to control access right
2. Allow to accept data with dynamic fields (Currently only accept data with fixed number of fields)
3. Add data value validation. There are no boundaries at the moment
4. Add swagger support for easy testing
5. Add auto rotating logs
6. Check for data duplication.
