package com.enid.rxjavacomm.util;

import com.enid.rxjavacomm.model.AddressInfoModel;
import com.enid.rxjavacomm.model.AddressModel;

import rx.functions.Func1;

/**
 * Created by Enid on 2016/8/11.
 */
public class AddressModel2AddressInfoModel implements Func1<AddressModel,AddressInfoModel>{
    private static AddressModel2AddressInfoModel INSTANCE = new AddressModel2AddressInfoModel();
    @Override
    public AddressInfoModel call(AddressModel timeModel) {
        AddressInfoModel timeInfoModel = new AddressInfoModel();
        timeInfoModel.setId(0);
        timeInfoModel.setLevel(timeModel.getLevel());
        timeInfoModel.setLon(timeModel.getLon());
        timeInfoModel.setLat(timeModel.getLat());
        return timeInfoModel;
    }

    private AddressModel2AddressInfoModel(){
    }

    public static AddressModel2AddressInfoModel getInstance(){
        return INSTANCE;
    }
}
