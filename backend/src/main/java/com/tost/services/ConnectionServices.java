package com.tost.services;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.tost.models.User;
import org.apache.commons.lang.RandomStringUtils;

public class ConnectionServices {

    public static String createUser(String mail, String username, String password){

        DatabaseServices.openDB();

        //Check if already exist
        User myMan = User.findFirst("user_pseudo=?",username);
        if(myMan!=null){
            return "EXISTING_PSEUDO";
        }

        myMan = User.findFirst("user_mail=?",mail);
        if(myMan!=null){
            return "EXISTING_MAIL";
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

        DatabaseServices.closeDB();

        return "OK";
    }

    public static String checkCredentials(String username, String password){

        DatabaseServices.openDB();

        User myMan = User.findFirst("user_pseudo=?",username);
        if(myMan==null){
            return "BAD_CREDENTIALS";
        }
        String salt = myMan.get("user_salt").toString();

        String hashSimple = Hashing.sha256()
                .hashString(password, Charsets.UTF_8)
                .toString();
        String hashDouble = Hashing.sha256()
                .hashString(hashSimple+salt, Charsets.UTF_8)
                .toString();

        if(hashDouble.equals( myMan.get("user_pwd"))) {
            String id = myMan.getId().toString();
            DatabaseServices.closeDB();
            return "OK__"+id;
        }
        else
        {
            DatabaseServices.closeDB();
            return "BAD_CREDENTIALS";
        }
    }

}
