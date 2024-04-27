package Backend;
import DataStructure.Customer;

import java.util.Scanner;

public class LoginBackend {
    public static boolean checkLogin(Customer user, String userMail, String userPassword) {
        return user.geteMail().equals(userMail) && user.getPassword().equals(userPassword);
    }

    public static void createAccount(Customer user, String userName, String userFname, String userEmail, String password, String birthday, String address, String zipCode, String city) {}

}
