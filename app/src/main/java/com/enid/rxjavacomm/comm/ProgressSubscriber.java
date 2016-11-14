package com.enid.rxjavacomm.comm;

import android.content.Context;

import rx.Subscriber;

/**
 * Created by Enid on 2016/8/11.
 */
public class ProgressSubscriber<T> extends Subscriber<T> {
    private CustomProgressDialog progressDialog = null;
    private Context mContext;

    public ProgressSubscriber(Context context) {
        this.mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (progressDialog == null) {
            progressDialog = new CustomProgressDialog(mContext);
        }
        progressDialog.show();
    }

    @Override
    public void onCompleted() {
        cancelProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        cancelProgressDialog();
    }

    @Override
    public void onNext(T o) {

    }

    private void cancelProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }
}
