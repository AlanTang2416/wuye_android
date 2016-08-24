package com.base.baselibs.iimp;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.base.baselibs.util.CustomToast;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.MyCleanEditText;

/**
 * 描述 自定义EditView输入监听
 * 作者 tangbingliang
 * 时间 16/5/4 10:36
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MyTextWatcher implements TextWatcher {
    private EditText myCleanEditText;
    private boolean isDoc = false; //是否可以输入小数点
    private Context mContext;
    private int max;
    private int thismax;
    private String toastStr;
    private EditCheckBack mcheckBack;
    private boolean isCode = false;

    public MyTextWatcher(Context mContext, EditText myCleanEditText, boolean isDoc
            , int max, String toastStr, EditCheckBack mcheckBack){
        this.myCleanEditText = myCleanEditText;
        this.mContext = mContext;
        this.toastStr = toastStr;
        this.max = max;
        this.isDoc = isDoc;
        this.mcheckBack = mcheckBack;
    }

    public MyTextWatcher(Context mContext, EditText myCleanEditText, boolean isDoc
            , int max, String toastStr, EditCheckBack mcheckBack, boolean isCode){
        this.myCleanEditText = myCleanEditText;
        this.mContext = mContext;
        this.toastStr = toastStr;
        this.max = max;
        this.isDoc = isDoc;
        this.mcheckBack = mcheckBack;
        this.isCode = isCode;
    }

    public MyTextWatcher(MyCleanEditText myCleanEditText){
        this.myCleanEditText = myCleanEditText;
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
            mcheckBack.isNull();
            return;
        }
        if (content.contains(".")) {
            thismax = max + 3;
        } else {
            thismax = max;
        }
        int size = content.length();
        if (size > thismax && !toastStr.isEmpty()) {
            s.delete(thismax , size);
            CustomToast.showToast(mContext,toastStr,2000);
        }
        if (!isDoc) {//不可以有小数点
            if (content.endsWith(".")) { //最后输入的不是点，无需处理
                s.delete(size - 1, size);//之前有输入过点，删除重复输入的点
            }
            if (!isCode && content.startsWith("0")) {
                myCleanEditText.setText("");
            }
        } else {//只能输入一个小数点
            if (content.endsWith(".")) { //最后输入的不是点，无需处理
                if (content.substring(0, size - 1).contains(".")) { //判断之前有没有输入过点
                    s.delete(size - 1, size);//之前有输入过点，删除重复输入的点
                }
            } else {
                size = content.length();
                if (s.toString().contains(".") && size<=thismax) {
                    int i = s.toString().indexOf(".");
                    if (s.toString().substring(i, size).length() > 3) {
                        s.delete(i + 3, size);
                    }
                }
            }

            if (content.startsWith(".") && size==1) {
                s.insert(0, "0.");
            }

            if (content.startsWith("0") && size>=2 &&
                    !s.toString().contains(".") && Integer.parseInt(s.toString())>0) {
                s.delete(0, 1);
            }

            if (content.startsWith("00") && size==2) {
                s.delete(size - 1, size);
            }
        }
        mcheckBack.isNull();
    }
}
