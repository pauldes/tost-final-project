# Tøst innovative project

Tøst is a mobile application dedicated to discover new places to hang out to, alone or with your friends.
Tøst knows what you (and your friends) like most and is able to tell you where to go to enjoy life.

This project was realized during our last year at @INSAdeLyon.


## Getting started

### Prerequisites

You will need a mysql server, a Java http server like Jetty, and an Internet connection.

You also need Cordova and Maven.

### Running

Run the backend maven dependencies.

```
mvn install
```

Add a maven goal after the build

```
org.javalite:activejdbc-instrumentation:1.4.13:instrument
```

Run Jetty (backend) with 
* path:           /
* port:           8080
* classes folder: backend\target\classes
* webApp folder : backend\src\webapp

Run Cordova (frontend) with
* command:            run
* executable:         your_path_to\npm\cordova.cmd
* working directory:  frontend

### Use

* http://localhost:8080/api/ should show a welcome message from the server
* http://localhost:8000      should show the mobile app

### Install the gulp and sass dependencies

Install dependencies 

```
npm install gulp
npm install gulp-sass --save-dev
```

Run Gulp configuration with :

* gulpfile: _path-to-gulpfile.js_
* tasks: sass

### Built With

* [Onsen UI 2](https://onsen.io/) - UI framework and components for HTML5 hybrid mobile app
* [ActiveJDBC](http://javalite.io/activejdbc) - Fast Java ORM
* [Maven](https://maven.apache.org/) - Dependency Management
* IntelliJ Idea

## Authors

* **Paul D.** 
* **Soafara Z.** 
* **Alicia L.**


