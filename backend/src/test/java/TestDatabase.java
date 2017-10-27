import com.tost.models.User;
import org.javalite.activejdbc.Base;

import java.util.List;

public class TestDatabase {

    public static void main(String [ ] args){
        System.out.println("Connecting to the DB...");
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/tost_db", "root", "root");
        System.out.println("Storing John in the DB...");
        User u = new User();
        u.set("name", "John");
        u.set("mail", "john@gmail.com");
        u.saveIt();
        System.out.println("Searching for John from the DB...");
        List<User> people = User.where("name = 'John'");
        User aJohn = people.get(0);
        String johnsMail = aJohn.get("mail").toString();
        System.out.println("John found at "+johnsMail);
    }

}
