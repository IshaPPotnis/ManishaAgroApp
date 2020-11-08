package com.example.manishaagro.model;

import com.google.gson.annotations.SerializedName;

public class ExpenseModel {
    @SerializedName("response")
    private String Response;
    @SerializedName("value")
    public String value;
    @SerializedName("data1")
    public String data1;
    @SerializedName("data2")
    public String data2;
    @SerializedName("data3")
    public String data3;
    @SerializedName("data4")
    public String data4;
    @SerializedName("message")
    private String message;

    @SerializedName("expense_id")
    private int expenseid;
    @SerializedName("expense_name")
    private String expensename;
    @SerializedName("da")
    private double da;
    @SerializedName("out_da")
    private double outda;
    @SerializedName("lodgeT")
    private double lodget;
    @SerializedName("lodgeD")
    private double lodged;
    @SerializedName("mobile")
    private double mobile;
    @SerializedName("km_limit")
    private int kmlimit;

    public int getExpenseid() {
        return expenseid;
    }

    public String getExpensename() {
        return expensename;
    }

    public double getDa() {
        return da;
    }

    public double getOutda() {
        return outda;
    }

    public double getLodget() {
        return lodget;
    }

    public double getLodged() {
        return lodged;
    }

    public double getMobile() {
        return mobile;
    }

    public int getKmlimit() {
        return kmlimit;
    }

    public void setExpenseid(int expenseid) {
        this.expenseid = expenseid;
    }

    public void setExpensename(String expensename) {
        this.expensename = expensename;
    }

    public void setDa(double da) {
        this.da = da;
    }

    public void setOutda(double outda) {
        this.outda = outda;
    }

    public void setLodget(double lodget) {
        this.lodget = lodget;
    }

    public void setLodged(double lodged) {
        this.lodged = lodged;
    }

    public void setMobile(double mobile) {
        this.mobile = mobile;
    }

    public void setKmlimit(int kmlimit) {
        this.kmlimit = kmlimit;
    }

    public String getResponse() {
        return Response;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public String getData1() {
        return data1;
    }

    public String getData2() {
        return data2;
    }

    public String getData3() {
        return data3;
    }

    public String getData4() {
        return data4;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }

    public void setData4(String data4) {
        this.data4 = data4;
    }
}
