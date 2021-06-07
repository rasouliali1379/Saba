package com.rayanandishehnasr.hmi.DataHolder;

import com.google.gson.annotations.SerializedName;

public class SalesIntercourseBaseMuncItem{

	@SerializedName("Count")
	private int count;

	@SerializedName("MuncZone")
	private String muncZone;

	public int getCount(){
		return count;
	}

	public String getMuncZone(){
		return muncZone;
	}
}