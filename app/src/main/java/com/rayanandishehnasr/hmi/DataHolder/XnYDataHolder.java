package com.rayanandishehnasr.hmi.DataHolder;

import com.google.gson.annotations.SerializedName;

public class XnYDataHolder{

	@SerializedName("X")
	private String X;

	@SerializedName("Y")
	private String Y;

	public String getX(){
		return X;
	}

	public String getY(){
		return Y;
	}
}