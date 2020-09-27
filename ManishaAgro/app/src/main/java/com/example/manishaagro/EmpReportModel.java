package com.example.manishaagro;

import com.google.gson.annotations.SerializedName;

public class EmpReportModel {
    @SerializedName("response")
    private String Response;

    @SerializedName("value")
    public String value;
    @SerializedName("message")
    private String message;


    @SerializedName("emp_id")
    private String empid;
    @SerializedName("reports_to_emp_id")
    private String reportstoempid;

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getReportstoempid()
    {
        return reportstoempid;
    }

    public void setReportstoempid(String reportstoempid) {
        this.reportstoempid = reportstoempid;
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
