package com.example.manishaagro.model;

import com.google.gson.annotations.SerializedName;

public class EmpIdDesignationModel {

    @SerializedName("emp_id")
    private String empId;
    @SerializedName("designation")
    private String designation;

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
