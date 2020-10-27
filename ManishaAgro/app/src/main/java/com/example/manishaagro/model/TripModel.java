package com.example.manishaagro.model;

import com.google.gson.annotations.SerializedName;

public class TripModel {
    @SerializedName("response")
    private String Response;
    @SerializedName("value")
    public String value;
    @SerializedName("message")
    private String message;
    @SerializedName("emp_id")
    private String empId;
    @SerializedName("visited_customer_name")
    private String visitedCustomerName;
    @SerializedName("date_of_travel")
    private String dateOfTravel;
    @SerializedName("date_of_return")
    private String dateOfReturn;
    @SerializedName("address")
    private String address;

    @SerializedName("demo_type")
    private String demotype;

    @SerializedName("village")
    private String village;

    @SerializedName("taluka")
    private String taluka;

    @SerializedName("district")
    private String district;

    @SerializedName("contact_detail")
    private String contactdetail;

    @SerializedName("crops")
    private String crops;
    @SerializedName("crop_health")
    private String crophealth;
    @SerializedName("demo_name")
    private String demoname;

    @SerializedName("usage_type")
    private String usagetype;

    @SerializedName("product_name")
    private String productname;

    @SerializedName("packing")
    private String packing;


    @SerializedName("product_quantity")
    private String productquantity;
    @SerializedName("water_quantity")
    private String waterquantity;

    @SerializedName("additions")
    private String additions;

    @SerializedName("follow_up_date")
    private String followupdate;

    @SerializedName("demo_image")
    private String  demoimage;


    @SerializedName("selfie_with_customer")
    private String  selfiewithcustomer;

    @SerializedName("follow_up_required")
    private int followuprequired;
    @SerializedName("observations")
    private String  observations;
    @SerializedName("customer_review")
    private String   customer_review;
    @SerializedName("follow_up_image")
    private String   follow_up_image;

    @SerializedName("customer_rating")
    private int customer_rating;

    @SerializedName("demo_required")
    private int demorequired;

    public int getDemorequired() {
        return demorequired;
    }

    public void setDemorequired(int demorequired) {
        this.demorequired = demorequired;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public int getCustomer_rating() {
        return customer_rating;
    }

    public void setCustomer_rating(int customer_rating) {
        this.customer_rating = customer_rating;
    }

    public String getCustomer_review() {
        return customer_review;
    }

    public void setCustomer_review(String customer_review) {
        this.customer_review = customer_review;
    }

    public String getFollow_up_image() {
        return follow_up_image;
    }

    public void setFollow_up_image(String follow_up_image) {
        this.follow_up_image = follow_up_image;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getDemoimage() {
        return demoimage;
    }

    public String getSelfiewithcustomer() {
        return selfiewithcustomer;
    }

    public void setDemoimage(String demoimage) {
        this.demoimage = demoimage;
    }

    public void setSelfiewithcustomer(String selfiewithcustomer) {
        this.selfiewithcustomer = selfiewithcustomer;
    }

    public String getFollowupdate() {
        return followupdate;
    }

    public void setFollowupdate(String followupdate) {
        this.followupdate = followupdate;
    }



    public int getFollowuprequired() {
        return followuprequired;
    }

    public void setFollowuprequired(int followuprequired) {
        this.followuprequired = followuprequired;
    }

    public String getProductquantity() {
        return productquantity;
    }

    public String getWaterquantity() {
        return waterquantity;
    }

    public String getAdditions() {
        return additions;
    }

    public void setProductquantity(String productquantity) {
        this.productquantity = productquantity;
    }

    public void setWaterquantity(String waterquantity) {
        this.waterquantity = waterquantity;
    }

    public void setAdditions(String additions) {
        this.additions = additions;
    }

    public String getUsagetype() {
        return usagetype;
    }

    public String getProductname() {
        return productname;
    }

    public void setUsagetype(String usagetype) {
        this.usagetype = usagetype;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCrops() {
        return crops;
    }

    public String getCrophealth() {
        return crophealth;
    }

    public String getDemoname() {
        return demoname;
    }

    public void setCrops(String crops) {
        this.crops = crops;
    }

    public void setCrophealth(String crophealth) {
        this.crophealth = crophealth;
    }

    public void setDemoname(String demoname) {
        this.demoname = demoname;
    }

    public String getContactdetail() {
        return contactdetail;
    }



    public void setContactdetail(String contactdetail) {
        this.contactdetail = contactdetail;
    }

    public String getTaluka() {
        return taluka;
    }

    public String getDistrict() {
        return district;
    }

    public String getVillage() {
        return village;
    }

    public String getDemotype() {
        return demotype;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setDemotype(String demotype) {
        this.demotype = demotype;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpId() {
        return empId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setVisitedCustomerName(String visitedCustomerName) {
        this.visitedCustomerName = visitedCustomerName;
    }

    public String getVisitedCustomerName() {
        return visitedCustomerName;
    }

    public void setDateOfTravel(String dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public String getDateOfTravel() {
        return dateOfTravel;
    }

    public void setDateOfReturn(String dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public String getDateOfReturn() {
        return dateOfReturn;
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
