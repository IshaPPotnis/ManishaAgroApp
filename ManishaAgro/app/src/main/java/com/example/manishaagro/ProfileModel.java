package com.example.manishaagro;

import com.google.gson.annotations.SerializedName;

public class ProfileModel {
    @SerializedName("response")
     private String Response;

    @SerializedName("value")
    public String value;
    @SerializedName("message")
    private String message;

    @SerializedName("emp_id")
    private String empid;
    @SerializedName("user_name")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    @SerializedName("designation")
    private String designation;

    @SerializedName("dob")
    private String dob;

    @SerializedName("joining_date")
    private String joiningdate;

    @SerializedName("email_id")
    private String email;

    @SerializedName("contact_detail")
    private String contactdtl;

    @SerializedName("mobile")
    private String mobile;



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

    public String getEmpid()
    {
        return empid;

    }
    public void setEmpid(String empid )
    {
        this.empid=empid;
    }

   public String getUsername(){return username; }
   public void setUsername(String username){
        this.username=username;
   }

    public String getPassword() {
        return password;
    }

    public void setPaswword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getJoiningdate() {
        return joiningdate;
    }

    public void setJoiningdate(String joiningdate) {
        this.joiningdate = joiningdate;
    }


    public String getContactdtl() {
        return contactdtl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }



    public void setContactdtl(String contactdtl) {
        this.contactdtl =contactdtl;
    }







}
