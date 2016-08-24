package com.base.baselibs.iimp;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.base.baselibs.util.CustomToast;

/**
 * 描述 自定义EditView输入监听
 * 作者 tangbingliang
 * 时间 16/5/4 10:36
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class LimitSizeTextWatcher implements TextWatcher {
    private EditText myCleanEditText;
    private Context mContext;
    private int max;
    private String toastStr;

    public LimitSizeTextWatcher(Context mContext, EditText myCleanEditText, int max, String toastStr){
        this.myCleanEditText = myCleanEditText;
        this.mContext = mContext;
        this.toastStr = toastStr;
        this.max = max;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String content = s == null ? null : s.toString();
        if (s == null || s.length() == 0) {
            return;
        }
        int size = content.trim().length();
        if (size > max && !toastStr.isEmpty()) {
            s.delete(max , size);
            CustomToast.showToast(mContext,toastStr,2000);
        }
    }

}
