package com.example.manishaagro.model;

import com.google.gson.annotations.SerializedName;

public class MeterModel {
    @SerializedName("response")
    private String Response;
    @SerializedName("value")
    public String value;
    @SerializedName("message")
    private String message;
    @SerializedName("emp_id")
    private String empid;
    @SerializedName("start_meter_reading")
    private int startmeterreading;
    @SerializedName("end_meter_reading")
    private int endmeterreading;

    @SerializedName("start_dates")
    private String datestart;
    @SerializedName("end_dates")
    private String dateend;

    public String getResponse() {
        return Response;
    }

    public String getMessage() {
        return message;
    }

    public String getValue() {
        return value;
    }

    public String getEmpid() {
        return empid;
    }



    public int getEndmeterreading() {
        return endmeterreading;
    }

    public int getStartmeterreading() {
        return startmeterreading;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getDatestart() {
        return datestart;
    }

    public String getDateend() {
        return dateend;
    }

    public void setDateend(String dateend) {
        this.dateend = dateend;
    }

    public void setDatestart(String datestart) {
        this.datestart = datestart;
    }

    public void setStartmeterreading(int startmeterreading) {
        this.startmeterreading = startmeterreading;
    }

    public void setEndmeterreading(int endmeterreading) {
        this.endmeterreading = endmeterreading;
    }
}
