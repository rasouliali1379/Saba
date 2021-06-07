package com.rayanandishehnasr.hmi.Retrofit;

import com.rayanandishehnasr.hmi.DataHolder.CitiesDataHolder;
import com.rayanandishehnasr.hmi.DataHolder.DashBoardRootDataHolder;
import com.rayanandishehnasr.hmi.DataHolder.LocationRootDataHolder;
import com.rayanandishehnasr.hmi.DataHolder.SearchQueryDataModel;
import com.rayanandishehnasr.hmi.DataHolder.SearchResultDataHolder;
import com.rayanandishehnasr.hmi.DataHolder.XnYDataHolder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @POST("ModelMobile")
    Call<LocationRootDataHolder> ModelMobile(@Query("tiState")int state);

    @POST("SearchIntercourse")
    Call<List<SearchResultDataHolder>> SearchTrades(@Body SearchQueryDataModel searchQueryDataModel, @Query("tiState")int state);

    @POST("Conected")
    Call<Integer> CheckConnection(@Query("Input") int requestCode);

    @POST("SearchIntercourseBaseRadious")
    Call<List<SearchResultDataHolder>> SearchByCoordinates(@Query("flat") double Lat, @Query("flon") double Long, @Query("tiTreatyType") int treatyType, @Query("tiState")int state, @Query("iRadious") int radius);

    @POST("DashBoard")
    Call<DashBoardRootDataHolder> DashBoard(@Query("tiState")int state);

    @POST("GetAllState")
    Call<List<CitiesDataHolder>> GetAllState();

    @POST("Chart_CountBaseMuncOnMonth")
    Call<List<XnYDataHolder>> GetTradePerSection(@Query("tiState")int state, @Query("strYearAndMonth") String date);

    @POST("Chart_PriceBaseMuncOnMonth")
    Call<List<XnYDataHolder>> GetAveragePerSection(@Query("tiState")int state, @Query("strYearAndMonth") String date);

    @POST("Chart_CountBaseMonthOnYear")
    Call<List<XnYDataHolder>> GetTradePerMonth(@Query("tiState")int state, @Query("strYearAndMonth") String date);

    @POST("Chart_PriceBaseMonthOnYear")
    Call<List<XnYDataHolder>> GetAveragePerMonth(@Query("tiState")int state, @Query("strYearAndMonth") String date);

    @POST("Chart_CountBaseZoneAtMunc")
    Call<List<XnYDataHolder>> GetTradePerZone(@Query("tiState")int state, @Query("strYearAndMonth") String date, @Query("tiMunZone") int section, @Query("strEndDate") String endDate);

    @POST("Chart_PriceBaseZoneAtMunc")
    Call<List<XnYDataHolder>> GetAveragePerZone(@Query("tiState")int state, @Query("strYearAndMonth") String date, @Query("tiMunZone") int section, @Query("strEndDate") String endDate);

//    @POST("GetAllByItem")
//    Call<List<DefaultDataForMobileResult>> GetAllByItem(@Body SearchItemData model);
//
//
//    //گرفتن لیست معاملات آن روز بصورت پیش فرض
//    @POST("GetDefaultData")
//    Call<List<DefaultDataForMobileResult>> GetDefaultData(@Body Items items);
//
//    //نمایش جزییات معامله
//    @POST("GetAllDetails")
//    Call<List<AllDetails>> JsonCodeForDetails(@Body JsonCodeForDetails model);
//
//    @POST("")
//    Call<List<ChartParameter>> GetParameter();
}
