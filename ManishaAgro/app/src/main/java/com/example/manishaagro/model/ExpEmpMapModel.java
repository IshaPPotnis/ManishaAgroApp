package com.example.manishaagro.model;

import com.google.gson.annotations.SerializedName;

public class ExpEmpMapModel {

    @SerializedName("response")
    private String Response;
    @SerializedName("value")
    public String value;
    @SerializedName("message")
    private String message;

    @SerializedName("expense_id")
    private int expenseid;
    @SerializedName("emp_id")
    private String emp_id;


    public String getResponse() {
        return Response;
    }

    public String getMessage() {
        return message;
    }

    public String getValue() {
        return value;
    }

    public int getExpenseid() {
        return expenseid;
    }

    public String getEmp_id() {
        return emp_id;
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

    public void setExpenseid(int expenseid) {
        this.expenseid = expenseid;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }
}
