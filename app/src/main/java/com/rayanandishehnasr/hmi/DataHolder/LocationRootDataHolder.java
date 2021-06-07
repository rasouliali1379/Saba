package com.rayanandishehnasr.hmi.DataHolder;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LocationRootDataHolder{

	@SerializedName("GetAllZone")
	private List<GetAllZoneItem> getAllZone;

	@SerializedName("GetState")
	private List<GetStateItem> getState;

	@SerializedName("GetAllmunZone")
	private List<GetAllmunZoneItem> getAllmunZone;

	public List<GetAllZoneItem> getGetAllZone(){
		return getAllZone;
	}

	public List<GetStateItem> getGetState(){
		return getState;
	}

	public List<GetAllmunZoneItem> getGetAllmunZone(){
		return getAllmunZone;
	}
}