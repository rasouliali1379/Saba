package com.rayanandishehnasr.hmi.DataHolder;

import com.google.gson.annotations.SerializedName;

public class GetAllZoneItem{

	@SerializedName("iMunZone")
	private int iMunZone;

	@SerializedName("iZone")
	private int iZone;

	@SerializedName("strZoneName")
	private String strZoneName;

	public int getIMunZone(){
		return iMunZone;
	}

	public int getIZone(){
		return iZone;
	}

	public String getStrZoneName(){
		return strZoneName;
	}
}