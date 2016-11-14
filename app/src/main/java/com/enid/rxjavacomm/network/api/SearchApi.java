package com.enid.rxjavacomm.network.api;

import com.enid.rxjavacomm.model.AddressModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Enid on 2016/8/10.
 */
public interface SearchApi {
    @GET("/geocoding")
    Observable<AddressModel> search(@Query("a") String city);
}
