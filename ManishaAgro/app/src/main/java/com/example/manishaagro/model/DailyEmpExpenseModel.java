package com.example.manishaagro.model;

import com.google.gson.annotations.SerializedName;

public class DailyEmpExpenseModel {

    @SerializedName("response")
    private String Response;
    @SerializedName("value")
    public String value;
    @SerializedName("data1")
    public String data1;
    @SerializedName("data2")
    public String data2;

    @SerializedName("message")
    private String message;

    @SerializedName("emp_id")
    private String empid;
    @SerializedName("cur_date")
    private String curdate;
    @SerializedName("da")
    private double da;
    @SerializedName("out_da")
    private double outda;
    @SerializedName("lodgeT")
    private double lodget;
    @SerializedName("lodgeD")
    private double lodged;

    @SerializedName("total_km")
    private int kmlimit;

    public String getResponse() {
        return Response;
    }

    public String getValue() {
        return value;
    }

    public String getData1() {
        return data1;
    }

    public String getData2() {
        return data2;
    }

    public String getMessage() {
        return message;
    }

    public String getEmpid() {
        return empid;
    }

    public String getCurdate() {
        return curdate;
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

    public int getKmlimit() {
        return kmlimit;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDa(double da) {
        this.da = da;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public void setCurdate(String curdate) {
        this.curdate = curdate;
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

    public void setKmlimit(int kmlimit) {
        this.kmlimit = kmlimit;
    }
}
