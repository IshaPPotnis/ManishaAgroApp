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
    private String empid;
    @SerializedName("visited_customer_name")
    private String vstcustname;
    @SerializedName("date_of_travel")
    private String dttravel;
    @SerializedName("date_of_return")
    private String dtreturn;
    @SerializedName("address")
    private String address;

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getEmpid() {
        return empid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setVstcustname(String vstcustname) {
        this.vstcustname = vstcustname;
    }

    public String getVstcustname() {
        return vstcustname;
    }

    public void setDttravel(String dttravel) {
        this.dttravel = dttravel;
    }

    public String getDttravel() {
        return dttravel;
    }

    public void setDtreturn(String dtreturn) {
        this.dtreturn = dtreturn;
    }

    public String getDtreturn() {
        return dtreturn;
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
