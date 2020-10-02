package com.example.manishaagro;

import com.google.gson.annotations.SerializedName;

public class TripModel {
    @SerializedName("response")
    private String Response;
    @SerializedName("value")
    public String value;
    @SerializedName("message")
    private String message;
    @SerializedName("emp_id")
    private String empId;
    @SerializedName("visited_customer_name")
    private String visitedCustomerName;
    @SerializedName("date_of_travel")
    private String dateOfTravel;
    @SerializedName("date_of_return")
    private String dateOfReturn;
    @SerializedName("address")
    private String address;

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpId() {
        return empId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setVisitedCustomerName(String visitedCustomerName) {
        this.visitedCustomerName = visitedCustomerName;
    }

    public String getVisitedCustomerName() {
        return visitedCustomerName;
    }

    public void setDateOfTravel(String dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public String getDateOfTravel() {
        return dateOfTravel;
    }

    public void setDateOfReturn(String dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public String getDateOfReturn() {
        return dateOfReturn;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMassage() {
        return message;
    }

    public void setMassage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return Response;
    }

}
