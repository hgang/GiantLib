package com.pisen.baselib.demo.retrofit;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by yangbo on 2016/4/1.
 *
 * @version 1.0
 */
public class BaseActivity extends Activity {

    ProgressDialog progressDialog;

    protected void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("加载中...");
        }
        progressDialog.show();
    }

    protected void dimissProgressDialog() {
        if (progressDialog != null) progressDialog.dismiss();
    }


    protected Activity getActivity() {
        return this;
    }

}
