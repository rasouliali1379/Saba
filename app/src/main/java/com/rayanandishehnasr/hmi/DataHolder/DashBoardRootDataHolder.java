package com.rayanandishehnasr.hmi.DataHolder;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DashBoardRootDataHolder{

	@SerializedName("RentCount")
	private int rentCount;

	@SerializedName("AverageRentingprices")
	private int averageRentingprices;

	@SerializedName("SalesIntercourseBaseMunc")
	private List<SalesIntercourseBaseMuncItem> salesIntercourseBaseMunc;

	@SerializedName("RentIntercourseBaseMunc")
	private List<RentIntercourseBaseMuncItem> rentIntercourseBaseMunc;

	@SerializedName("SellCount")
	private int sellCount;

	@SerializedName("Averagehousingprices")
	private int averagehousingprices;

	public int getRentCount(){
		return rentCount;
	}

	public int getAverageRentingprices(){
		return averageRentingprices;
	}

	public List<SalesIntercourseBaseMuncItem> getSalesIntercourseBaseMunc(){
		return salesIntercourseBaseMunc;
	}

	public List<RentIntercourseBaseMuncItem> getRentIntercourseBaseMunc(){
		return rentIntercourseBaseMunc;
	}

	public int getSellCount(){
		return sellCount;
	}

	public int getAveragehousingprices(){
		return averagehousingprices;
	}
}