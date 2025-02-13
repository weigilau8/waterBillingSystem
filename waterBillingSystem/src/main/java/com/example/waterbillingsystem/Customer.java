package com.example.waterbillingsystem;

public class Customer {
    private int CustomerID;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Phone;
    private String Address;
    private String City;
    private String State;
    private String ZipCode;
    private String ServiceCenter;

    public Customer(int CustomerID, String FirstName, String LastName, String Email, String Phone, String Address, String City, String State, String ZipCode, String ServiceCenter) {
        this.CustomerID = CustomerID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Phone = Phone;
        this.Address = Address;
        this.City = City;
        this.State = State;
        this.ZipCode = ZipCode;
        this.ServiceCenter = ServiceCenter;
    }

    // Getters
    public int getCustomerID() {
        return CustomerID;
    }
    public String getFirstName() {
        return FirstName;
    }
    public String getLastName() {
        return LastName;
    }
    public String getEmail() {
        return Email;
    }
    public String getPhone() {
        return Phone;
    }
    public String getAddress() {
        return Address;
    }
    public String getCity() {
        return City;
    }
    public String getState() {
        return State;
    }
    public String getZipCode() {
        return ZipCode;
    }
    public String getServiceCenter() {
        return ServiceCenter;
    }

    // Setters
    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }
    public void setFirstName(String Cust_First_Name) {
        this.FirstName = Cust_First_Name;
    }
    public void setLastName(String Cust_Last_Name) {
        this.LastName = Cust_Last_Name;
    }
    public void setEmail(String Service_Centre) {
        this.Email = Service_Centre;
    }
    public void setPhone(String Street) {
        this.Phone = Street;
    }
    public void setAddress(String City) {
        this.Address = City;
    }
    public void setCity(String City_Code) {
        this.City = City_Code;
    }
    public void setState(String State) {
        this.State = State;
    }
    public void setZipCode(String ZipCode) {
        this.ZipCode = ZipCode;
    }
    public void setServiceCenter(String ServiceCenter) {
        this.ServiceCenter = ServiceCenter;
    }
}
