package Backend;
import DataStructure.Customer;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginBackend {
    public static boolean checkLogin(Customer user, String userMail, String userPassword) {
        return user.geteMail().equals(userMail) && user.getPassword().equals(userPassword);
    }

    public static void createAccount(Customer user, String userName, String userFname, String userEmail, String password, String birthday, String address, String zipCode, String city) {
        DatabaseConnection db = new DatabaseConnection();
        try {
            PreparedStatement stmt = db.PrepareCreateAcoount(db.GetCommand(DatabaseConnection.Command.CreateAccount), userName, userFname, userEmail, password, birthday, address, zipCode, city);
            db.executeQueryPrepared(stmt);
        } catch (SQLException e){
        }
    }

    public static void deleteAccount(Customer user, String userEmail, String userPassword) {
        DatabaseConnection db = new DatabaseConnection();
        try {
            PreparedStatement stmt = db.PrepareDeleteAccount(db.GetCommand(DatabaseConnection.Command.DeleteAccount), userEmail, userPassword);
            db.executeQueryPrepared(stmt);
        } catch (SQLException e){
        }
    }

}
