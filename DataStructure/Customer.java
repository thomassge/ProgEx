package DataStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
public class Customer {
int id;
String name;
String fname;
String eMail;
String password;
Date birthday;
String address;
String zip_code;
String city;
ArrayList<Orders> orders;

    public Customer(int id, String name, String fname, String email, String password, Date birthday, String address, String zipCode, String city) {
        this.id = id;
        this.name = name;
        this.fname = fname;
        this.eMail = email;
        this.password = password;
        this.birthday = birthday;
        this.address = address;
        this.zip_code = zipCode;
        this.city = city;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<Orders> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Orders> orders) {
        this.orders = orders;

    }
    public void PrintPersonalBooks(){
      for(Orders o : orders){
         o.WriteOrderInConsole();
      }
    }

}
