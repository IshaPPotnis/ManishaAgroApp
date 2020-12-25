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

    @SerializedName("data3")
    public String data3;
    @SerializedName("data4")
    public String data4;

    @SerializedName("message")
    private String message;

    @SerializedName("emp_id")
    private String empid;


    @SerializedName("start_dates")
    private String stardate;

    @SerializedName("end_dates")
    private String enddate;

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


    @SerializedName("opening_km")
    private int startopening_km;
    @SerializedName("closing_km")
    private int endclosing_km;

    @SerializedName("other_expense_reason")
    private String other_expense_reason;

    @SerializedName("other_expense_amount")
    private double other_expense_amt;

    @SerializedName("bus_train")
    private double bustrain;
    @SerializedName("bike")
    private double bike;

    @SerializedName("driver")
    private double driver;

    @SerializedName("route")
    private String routes;

    @SerializedName("close_remark")
    private String close_remark;

    @SerializedName("halts")
    private int ishalt;

    @SerializedName("expstatus")
    private int expstatus;
    @SerializedName("place_type")
    private int placetype;
    @SerializedName("place_name")
    private String placename;


    @SerializedName("actual_discription")
    private String actual_discription;

    @SerializedName("actual_amount")
    private double actual_amt;

    @SerializedName("radio_travel")
    private int radio_travel;

    public String getClose_remark() {
        return close_remark;
    }

    public void setClose_remark(String close_remark) {
        this.close_remark = close_remark;
    }

    public String getData3() {
        return data3;
    }

    public String getData4() {
        return data4;
    }

    public void setData4(String data4) {
        this.data4 = data4;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }

    public int getRadio_travel() {
        return radio_travel;
    }

    public void setRadio_travel(int radio_travel) {
        this.radio_travel = radio_travel;
    }

    public double getActual_amt() {
        return actual_amt;
    }

    public String getActual_discription() {
        return actual_discription;
    }

    public void setActual_amt(double actual_amt) {
        this.actual_amt = actual_amt;
    }

    public void setActual_discription(String actual_discription) {
        this.actual_discription = actual_discription;
    }

    public int getExpstatus() {
        return expstatus;
    }

    public int getPlacetype() {
        return placetype;
    }

    public String getPlacename() {
        return placename;
    }

    public void setExpstatus(int expstatus) {
        this.expstatus = expstatus;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public void setPlacetype(int placetype) {
        this.placetype = placetype;
    }

    public int getIshalt() {
        return ishalt;
    }

    public void setIshalt(int ishalt) {
        this.ishalt = ishalt;
    }

    public double getBike() {
        return bike;
    }

    public void setBike(double bike) {
        this.bike = bike;
    }

    public String getRoutes() {
        return routes;
    }

    public void setRoutes(String routes) {
        this.routes = routes;
    }

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


    public double getBustrain() {
        return bustrain;
    }

    public double getDriver() {
        return driver;
    }

    public double getOther_expense_amt() {
        return other_expense_amt;
    }

    public int getEndclosing_km() {
        return endclosing_km;
    }

    public int getStartopening_km() {
        return startopening_km;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getOther_expense_reason() {
        return other_expense_reason;
    }

    public String getStardate() {
        return stardate;
    }

    public void setBustrain(double bustrain) {
        this.bustrain = bustrain;
    }

    public void setDriver(double driver) {
        this.driver = driver;
    }

    public void setEndclosing_km(int endclosing_km) {
        this.endclosing_km = endclosing_km;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public void setOther_expense_amt(double other_expense_amt) {
        this.other_expense_amt = other_expense_amt;
    }

    public void setOther_expense_reason(String other_expense_reason) {
        this.other_expense_reason = other_expense_reason;
    }

    public void setStardate(String stardate) {
        this.stardate = stardate;
    }

    public void setStartopening_km(int startopening_km) {
        this.startopening_km = startopening_km;
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
