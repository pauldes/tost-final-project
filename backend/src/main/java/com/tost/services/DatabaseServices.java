package com.tost.services;

import org.javalite.activejdbc.Base;

import java.util.List;
import java.util.Map;

public class DatabaseServices {

    public static void openDB(){
        if(!Base.hasConnection()) {
            //Local
            Base.open("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost/tost_db?serverTimezone=UTC&nullNamePatternMatchesAll=true&useSSL=false", "root", "root");
        }
    }

    public static void closeDB(){
        Base.close();
    }

    public static List<Map> rawSQL(String query){
        List<Map> result = Base.findAll(query);
        return result;
    }

}
