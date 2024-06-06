package Backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginBackend {
    static DatabaseConnection db = new DatabaseConnection();

    /**
     * This method compares the login data with the data that is stored inside the customer database.
     *
     * @param userMail
     * @param userPassword
     * @return
     */

    public static boolean checkLogin(String userMail, String userPassword) {
        try {
            PreparedStatement stmt = db.prepareGetCustomerByEmailAndPassword(DatabaseConnection.getCustomerByEmailAndPassword, userMail, userPassword);
            ResultSet rs = db.executeQueryPrepared(stmt);

            boolean b = Manager.setUser(DataProcessor.processCustomer(rs));
            if (b) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method creates a new account and stores the data in the customer database.
     *
     * @param userName
     * @param userFname
     * @param userEmail
     * @param password
     * @param birthday
     * @param address
     * @param zipCode
     * @param city
     */

    public static void createAccount(String userName, String userFname, String userEmail, String password, String birthday, String address, String zipCode, String city) {
        try {
            PreparedStatement stmt = db.prepareCreateAccount(DatabaseConnection.getCommand(DatabaseConnection.Command.CreateAccount), userName, userFname, userEmail, password, birthday, address, zipCode, city);
            db.executeQueryPreparedUpdate(stmt);
            checkLogin(userEmail, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method deletes an already existing account in the customer database.
     *
     * @param userEmail
     * @param userPassword
     */

    public static void deleteAccount(String userEmail, String userPassword) {
        try {
            PreparedStatement stmt = db.prepareDeleteAccount(DatabaseConnection.getCommand(DatabaseConnection.Command.DeleteAccount), userEmail, userPassword);
            db.executeQueryPreparedUpdate(stmt);
        } catch (SQLException e) {
        }
    }

    /**
     * This method edits an already existing account in the customer database.
     *
     * @param userName
     * @param userFname
     * @param userEmail
     * @param password
     * @param birthday
     * @param address
     * @param zip_code
     * @param city
     */
    public static void editAccount(String userName, String userFname, String userEmail, String password, String birthday, String address, String zip_code, String city) {
        String previousEmail = Manager.getUser().getEmail();
        try {
            PreparedStatement stmt = db.prepareEditAccount(DatabaseConnection.getCommand(DatabaseConnection.Command.EditAccount), userName, userFname, userEmail, password, birthday, address, zip_code, city, previousEmail);
            db.executeQueryPreparedUpdate(stmt);
        } catch (SQLException e) {
        }
    }
}
