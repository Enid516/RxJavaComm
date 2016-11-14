package com.enid.rxjavacomm.comm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.enid.rxjavacomm.R;


/**
 * Created by biglove-work on 2016/6/12.
 */
public class CustomProgressDialog extends ProgressDialog{
    public CustomProgressDialog(Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);
        setCanceledOnTouchOutside(false);
    }

}
