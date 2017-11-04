package com.tost.services;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.tost.models.User;
import org.apache.commons.lang.RandomStringUtils;
import org.javalite.activejdbc.Base;

public class ConnectionServices {

    public static void openDB(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/tost_db?serverTimezone=UTC&nullNamePatternMatchesAll=true", "root", "root");
    }

    public static void closeDB(){
        Base.close();
    }

    public static boolean createUser(String mail, String username, String password){

        openDB();

        //Check if already exist
        User myMan = User.findFirst("user_pseudo=?",username);
        if(myMan!=null){
            return false;
        }

        User u = new User();
        u.set("user_pseudo", username);
        u.set("user_mail", mail);

        String salt = RandomStringUtils.randomAlphanumeric(8);
        u.set("user_salt", salt);

        String hashSimple = Hashing.sha256()
                .hashString(password, Charsets.UTF_8)
                .toString();
        String hashDouble = Hashing.sha256()
                .hashString(hashSimple+salt, Charsets.UTF_8)
                .toString();
        u.set("user_pwd", hashDouble);

        u.saveIt();

        closeDB();

        return true;
    }

    public static boolean checkCredentials(String username, String password){

        openDB();

        User myMan = User.findFirst("user_pseudo=?",username);
        if(myMan==null){
            return false;
        }
        String salt = myMan.get("user_salt").toString();

        String hashSimple = Hashing.sha256()
                .hashString(password, Charsets.UTF_8)
                .toString();
        String hashDouble = Hashing.sha256()
                .hashString(hashSimple+salt, Charsets.UTF_8)
                .toString();

        if(hashDouble.equals( myMan.get("user_pwd"))) {
            closeDB();
            return true;
        }
        else
        {
            closeDB();
            return false;
        }
    }

}
