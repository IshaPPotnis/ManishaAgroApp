package com.example.manishaagro.model;

import com.google.gson.annotations.SerializedName;

public class EmpIdDesignationModel {
    @SerializedName("emp_id")
    private String empId;
    @SerializedName("designation")
    private String designation;
    @SerializedName("response")
    private String response;
    @SerializedName("value")
    public String value;
    @SerializedName("message")
    private String message;

    public void setValue(String value) {
        this.value = value;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public String getResponse() {
        return response;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
