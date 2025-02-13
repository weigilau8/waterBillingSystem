package com.example.waterbillingsystem;

public class Bill {
    private int billId;
    private String customerName;
    private String billDate;
    private int quantity;
    private String dueDate;
    private int unitsConsumed;
    private Double ratePerUnit;
    private Double fixedCharge;
    private String status;

    public Bill(int billId, String customerName, String billDate, String dueDate, int unitsConsumed, double ratePerUnit, double fixedCharge, String status) {
        this.billId = billId;
        this.customerName = customerName;
        this.billDate = billDate;
        this.dueDate = dueDate;
        this.unitsConsumed = unitsConsumed;
        this.ratePerUnit = ratePerUnit;
        this.fixedCharge = fixedCharge;
        this.status = status;
    }
    public int getBillId() {
        return billId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getBillDate() {
        return billDate;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getDueDate() {
        return dueDate;
    }
    public int getUnitsConsumed() {
        return unitsConsumed;
    }
    public double getRatePerUnit() {
        return ratePerUnit;
    }
    public double getFixedCharge() {
        return fixedCharge;
    }
    public String getStatus() {
        return status;
    }


    public void setBillId(int billId) {
        this.billId = billId;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    public void setUnitsConsumed(int unitsConsumed) {
        this.unitsConsumed = unitsConsumed;
    }
    public void setRatePerUnit(double ratePerUnit) {
        this.ratePerUnit = ratePerUnit;
    }
    public void setFixedCharge(double fixedCharge) {
        this.fixedCharge = fixedCharge;
    }
    public void setStatus(String status) {
        this.status = status;
    }



}
