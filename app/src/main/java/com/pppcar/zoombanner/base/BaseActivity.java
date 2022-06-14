package com.pppcar.zoombanner.base;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.blankj.utilcode.util.ToastUtils;
import com.hjq.http.listener.OnHttpListener;
import com.pppcar.zoombanner.R;
import com.pppcar.zoombanner.network.HttpData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import okhttp3.Call;

/**
 * Author  ： logan
 * Time    ： 2022/5/31 10:49 上午
 * Desc    ：
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity implements OnHttpListener<Object> {
    protected T binding;
    /** 加载对话框 */
    private ProgressDialog mDialog;
    /** 对话框数量 */
    private int mDialogTotal;

    /**
     * 当前加载对话框是否在显示中
     */
    public boolean isShowDialog() {
        return mDialog != null && mDialog.isShowing();
    }

    /**
     * 显示加载对话框
     */
    public void showDialog() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
            mDialog.setMessage(getResources().getString(R.string.http_loading));
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        mDialogTotal++;
    }

    /**
     * 隐藏加载对话框
     */
    public void hideDialog() {
        if (mDialogTotal == 1) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
        if (mDialogTotal > 0) {
            mDialogTotal--;
        }
    }

    public T getBinding(){
        return binding;
    }
    protected void setContentView(Class<T> clz){
        try {
            Method inflate = clz.getMethod("inflate", LayoutInflater.class);
            binding = (T) inflate.invoke(clz,getLayoutInflater());
            if (binding != null) {
                setContentView(binding.getRoot());
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSucceed(Object result) {
        if (result instanceof HttpData && !TextUtils.isEmpty(((HttpData<?>) result).getMessage())) {
            ToastUtils.showShort(((HttpData<?>) result).getMessage());
        }
    }

    @Override
    public void onFail(Exception e) {
        ToastUtils.showShort(e.getMessage());
    }

    @Override
    public void onStart(Call call) {
        showDialog();
    }

    @Override
    public void onEnd(Call call) {
        hideDialog();
    }
}
