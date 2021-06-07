package com.rayanandishehnasr.hmi.DataHolder;

import com.google.gson.annotations.SerializedName;

public class GetAllmunZoneItem{

	@SerializedName("tiMuncipalty")
	private int tiMuncipalty;

	@SerializedName("iMunZone")
	private int iMunZone;

	public int getTiMuncipalty(){
		return tiMuncipalty;
	}

	public int getIMunZone(){
		return iMunZone;
	}
}