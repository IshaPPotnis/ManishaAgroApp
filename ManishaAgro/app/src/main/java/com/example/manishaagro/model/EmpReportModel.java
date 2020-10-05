package com.example.manishaagro.model;

import com.google.gson.annotations.SerializedName;

public class EmpReportModel {
    @SerializedName("response")
    private String response;
    @SerializedName("value")
    public String value;
    @SerializedName("message")
    private String message;
    @SerializedName("emp_id")
    private String empId;
    @SerializedName("reports_to_emp_id")
    private String reportsToEmpId;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getReportsToEmpId() {
        return reportsToEmpId;
    }

    public void setReportsToEmpId(String reportsToEmpId) {
        this.reportsToEmpId = reportsToEmpId;
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
        return response;
    }
}
