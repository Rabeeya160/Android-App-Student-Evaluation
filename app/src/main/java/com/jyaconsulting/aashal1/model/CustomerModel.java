package com.jyaconsulting.aashal1.model;

public class CustomerModel {
    String customerId,customer,customerAddress,customerPhone;

    public CustomerModel(String customerId, String customer, String customerAddress, String customerPhone) {
        this.customerId = customerId;
        this.customer = customer;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}
