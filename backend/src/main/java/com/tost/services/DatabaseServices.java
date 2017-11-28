package com.tost.services;

import org.javalite.activejdbc.Base;

public class DatabaseServices {

    public static void openDB(){
        if(!Base.hasConnection()) {
            Base.open("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost/tost_db?serverTimezone=UTC&nullNamePatternMatchesAll=true&useSSL=false", "root", "root");
        }
    }

    public static void closeDB(){
        Base.close();
    }

}
