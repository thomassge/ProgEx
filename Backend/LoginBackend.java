package Backend;
import DataStructure.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginBackend {
   static DatabaseConnection db = new DatabaseConnection();

    public static boolean checkLogin(String userMail, String userPassword) {
        try {
            PreparedStatement stmt = db.PrepareGetCustomerByEmailAndPassword(db.getCustomerByEmailAndPassword, userMail, userPassword);
            ResultSet rs = db.executeQueryPrepared(stmt);

          boolean b =  Manager.SetUser(DataProcessor.processCustomer(rs));
            if (b) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createAccount(String userName, String userFname, String userEmail, String password, String birthday, String address, String zipCode, String city) {
        try {
            PreparedStatement stmt = db.PrepareCreateAccount(db.GetCommand(DatabaseConnection.Command.CreateAccount), userName, userFname, userEmail, password, birthday, address, zipCode, city);
            db.executeQueryPreparedUpdate(stmt);
            checkLogin(userEmail, password);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static void deleteAccount(String userEmail, String userPassword) {
        try {
            PreparedStatement stmt = db.PrepareDeleteAccount(db.GetCommand(DatabaseConnection.Command.DeleteAccount), userEmail, userPassword);
            db.executeQueryPreparedUpdate(stmt);
        } catch (SQLException e){
        }
    }

    public static void editAccount(Customer user, String userName, String userFname, String userEmail, String password, String birthday, String address, String zip_code, String city) {
       Manager manager = new Manager();
       String previousEmail = manager.GetUser().geteMail();
        try {
            PreparedStatement stmt = db.PrepareEditAccount(db.GetCommand(DatabaseConnection.Command.EditAccount), userName, userFname, userEmail, password, birthday, address, zip_code, city, previousEmail);
            db.executeQueryPreparedUpdate(stmt);
        } catch (SQLException e) {
        }
    }
}
