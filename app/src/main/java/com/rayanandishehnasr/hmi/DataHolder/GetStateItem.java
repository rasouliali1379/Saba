package com.rayanandishehnasr.hmi.DataHolder;

import com.google.gson.annotations.SerializedName;

public class GetStateItem{

	@SerializedName("strStateName")
	private String strStateName;

	@SerializedName("tiState")
	private int tiState;

	public String getStrStateName(){
		return strStateName;
	}

	public int getTiState(){
		return tiState;
	}
}