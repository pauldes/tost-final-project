package com.tost.services;

import org.javalite.activejdbc.Base;

import java.util.List;
import java.util.Map;

public class DatabaseServices {

    public static void openDB(){
        if(!Base.hasConnection()) {
            //Local
            //Base.open("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost/tost_db?serverTimezone=UTC&nullNamePatternMatchesAll=true&useSSL=false", "root", "");
            //Helios
            //Base.open("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost/tost_db?serverTimezone=UTC&nullNamePatternMatchesAll=true&useSSL=false", "tost_app", "P=Fevq]LHPT#");
            //OpenShift
            Base.open("com.mysql.cj.jdbc.Driver", "jdbc:mysql://mysql:3306/tost_db?serverTimezone=UTC&nullNamePatternMatchesAll=true&useSSL=false", "tost_app", "pIRJAsjiqIt~p3");
        }
    }

    public static void closeDB(){
        if(Base.hasConnection()) {
            Base.close();
        }
    }

    public static List<Map> rawSQL(String query){
        List<Map> result = Base.findAll(query);
        return result;
    }

}
