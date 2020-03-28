# INTRODUCTION TO CICLISMO MEXICALI
Ciclismo Mexicali is software written for the Mexicali, Mexico and all cyclist visiting this city.

## Installation instructions

### Requirements
- JDBC
- MySQL
- Clojure
- Leiningen (Preferred method is to install Clojure with leiningen)
- Your preferred editor ex. vim, emacs, vscode [configure editor for clojure development]

### Installation
- Create a database with any MySQL client and name it what makes sense to you.  I called mine **cm**.
- In the file src/sk/models/cdb.clj you will find all the table definition needed.
- The configuration file needs to be created.  To do this you need to do the following:
    1. From the root of your application: **mkdir resources/private**
    2. From the root of your application: **touch resources/private/config.clj**
    3. The config file referenced above **config.clj** should have the following:

       `{:db-protocl "mysql"
           :db-name "//localhost:3306/**your-database-name**?characterEncoding=UTF-8"
           :db-user "your-database-user"
           :db-pwd "your-database-password
           :db-class "com.mysql.cj.jdbc.Driver"
           :email-host "your-email-host"
           :email-user "your-email-user"
           :email-pwd "your-email-password"
           :port 3000
           :tz "US/Pacific"
           :site-name "Ciclismo Mexicali"
           :base-url "http://0.0.0.0:3000/"
           :uploads "./uploads/"
           :path "/uploads/"}`



