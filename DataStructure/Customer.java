package DataStructure;

import java.util.ArrayList;
import java.util.Date;

/**
 * The Customer class represents a customer in the program.
 */
public class Customer {
    int id;
    String name;
    String fname;
    String email;
    String password;
    Date birthday;
    String address;
    String zipCode;
    String city;
    ArrayList<Order> orders;

    public Customer(int id, String name, String fname, String email, String password, Date birthday, String address, String zipCode, String city) {
        this.id = id;
        this.name = name;
        this.fname = fname;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String eMail) {
        this.email = eMail;
    }

    public String getPassword() {
        return password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;

    }

    public Order getOrderForBook(int bookId) {
        ArrayList<Order> allOrdersforBook = new ArrayList<>();

        for (Order order : orders) {
            if (order.getBook().getId() == bookId) {
                allOrdersforBook.add(order);
            }
        }

        return getOrderWithTheEarliestLendingDate(allOrdersforBook);
    }

    private Order getOrderWithTheEarliestLendingDate(ArrayList<Order> allOrdersforBook) {
        Order orderWithEarliestLendingDate = allOrdersforBook.getFirst();
        for (Order order : allOrdersforBook) {
            if (order.getOrderDate().before(orderWithEarliestLendingDate.getOrderDate())) {
                orderWithEarliestLendingDate = order;
            }
        }
        return orderWithEarliestLendingDate;
    }
}
