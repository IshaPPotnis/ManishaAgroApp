package com.example.manishaagro.model;

import com.google.gson.annotations.SerializedName;

public class DealerModel {

    @SerializedName("response")
    private String Response;
    @SerializedName("value")
    public String value;
    @SerializedName("message")
    private String message;
    @SerializedName("emp_id")
    private String empId;

    @SerializedName("dealer_name")
    private String dealername;

    @SerializedName("date_of_purchase")
    private String date_of_purchase;
    @SerializedName("dealer_id")
    private int dealer_id;

    public int getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(int dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDealername() {
        return dealername;
    }

    public void setDealername(String dealername) {
        this.dealername = dealername;
    }

    public String getDate_of_purchase() {
        return date_of_purchase;
    }

    public void setDate_of_purchase(String date_of_purchase) {
        this.date_of_purchase = date_of_purchase;
    }



}
