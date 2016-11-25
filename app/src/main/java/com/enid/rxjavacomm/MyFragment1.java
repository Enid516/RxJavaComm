package com.enid.rxjavacomm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enid.rxjavacomm.model.AddressInfoModel;
import com.enid.rxjavacomm.model.AddressModel;
import com.enid.rxjavacomm.network.ResponseHelper;

import rx.Observer;
import rx.Subscriber;
import rx.Subscription;


/**
 * Created by Enid on 2016/8/10.
 */
public class MyFragment1 extends Fragment {
    private static final String TAG = "MyFragment1";
    private Subscription subscription;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_1,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (subscription != null && subscription.isUnsubscribed())
            subscription.unsubscribe();

        getActivity().findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        subscription.unsubscribe();
    }

    private void click(){
        unsubscribe();
        ResponseHelper.search(subscription,subscriber,"a");//常规
        ResponseHelper.searchChange(subscription,observer);//map
        ResponseHelper.testFlatMap(subscription,observer);
        ResponseHelper.testZip(subscription);
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscriber.unsubscribe();
    }

    Observer<AddressInfoModel> observer = new Observer<AddressInfoModel>() {
        @Override
        public void onCompleted() {//请求成功后调用
            Log.d(TAG,"------>s :" + "onCompleted");
        }

        @Override
        public void onError(Throwable e) {//请求失败
            Log.d(TAG,"------>s :" + "onError:" + e.toString());
        }

        @Override
        public void onNext(AddressInfoModel s) {//请求成功
            Log.d(TAG,"------>s :" + s.toString());
            Log.d(TAG,"------>s.getLevel :" + s.getLevel());
            Log.d(TAG,"------>s.getLon :" + s.getLon());
            Log.d(TAG,"------>s.getLat :" + s.getLat());
        }
    };

    private Subscriber<AddressModel> subscriber = new Subscriber<AddressModel>(){
        @Override
        public void onStart() {
            super.onStart();
            Log.d(TAG,">s :" + "onStart");
        }

        @Override
        public void onCompleted() {
            Log.d(TAG,">s :" + "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG,">s :" + "onError:" + e.toString());
        }

        @Override
        public void onNext(AddressModel addressModel) {
            Log.d(TAG,">s :" + addressModel.toString());
            Log.d(TAG,">addressModel.getLevel :" + addressModel.getLevel());
            Log.d(TAG,">addressModel.getLat :" + addressModel.getLat());
            Log.d(TAG,">addressModel.getLon :" + addressModel.getLon());
        }
    };
}
