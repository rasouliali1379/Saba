package com.rayanandishehnasr.hmi.DataHolder;

import com.google.gson.annotations.SerializedName;

public class CitiesDataHolder{

	@SerializedName("strStateName")
	private String strStateName;

	@SerializedName("fLat")
	private double fLat;

	@SerializedName("fLon")
	private double fLon;

	@SerializedName("tiState")
	private int tiState;

	public String getStrStateName(){
		return strStateName;
	}

	public double getFLat(){
		return fLat;
	}

	public double getFLon(){
		return fLon;
	}

	public int getTiState(){
		return tiState;
	}
}