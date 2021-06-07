package com.rayanandishehnasr.hmi.DataHolder;

import com.google.gson.annotations.SerializedName;

public class SearchQueryDataModel {

    @SerializedName("TreatyType")
    private int TreatyType;

    @SerializedName("MunZone")
    private int MunZone;

    @SerializedName("Zone")
    private int Zone;

    @SerializedName("StartAgeInTreatyDate")
    private int StartAgeInTreatyType;

    @SerializedName("EndAgeInTreatyDate")
    private int EndAgeInTreatyType;

    @SerializedName("StartPrice")
    private int StartPrice;

    @SerializedName("EndPrice")
    private int EndPrice;

    @SerializedName("StartTotalPrice")
    private int StartTotalPrice;

    @SerializedName("EndTotalPrice")
    private int EndTotalPrice;

    @SerializedName("StartDate")
    private String StartDate;

    @SerializedName("EndDate")
    private String EndDate;



    public int getTreatyType() {
        return TreatyType;
    }

    public void setTreatyType(int treatyType) {
        TreatyType = treatyType;
    }

    public int getMunZone() {
        return MunZone;
    }

    public void setMunZone(int munZone) {
        MunZone = munZone;
    }

    public int getZone() {
        return Zone;
    }

    public void setZone(int zone) {
        Zone = zone;
    }

    public int getStartAgeInTreatyType() {
        return StartAgeInTreatyType;
    }

    public void setStartAgeInTreatyType(int startAgeInTreatyType) {
        StartAgeInTreatyType = startAgeInTreatyType;
    }

    public int getEndAgeInTreatyType() {
        return EndAgeInTreatyType;
    }

    public void setEndAgeInTreatyType(int endAgeInTreatyType) {
        EndAgeInTreatyType = endAgeInTreatyType;
    }

    public int getStartPrice() {
        return StartPrice;
    }

    public void setStartPrice(int startPrice) {
        StartPrice = startPrice;
    }

    public int getEndPrice() {
        return EndPrice;
    }

    public void setEndPrice(int endPrice) {
        EndPrice = endPrice;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public int getStartTotalPrice() {
        return StartTotalPrice;
    }

    public void setStartTotalPrice(int startTotalPrice) {
        StartTotalPrice = startTotalPrice;
    }

    public int getEndTotalPrice() {
        return EndTotalPrice;
    }

    public void setEndTotalPrice(int endTotalPrice) {
        EndTotalPrice = endTotalPrice;
    }
}
