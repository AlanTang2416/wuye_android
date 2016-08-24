package com.base.baselibs.iimp;

import android.view.View;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/28 16:09
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public interface IInit {

    /**
     * 初始化基本参数
     * 如Intent 及 成员变量初始化
     */
    public void initIntentAndMemData();

    /**
     * 组件启动时启用网络获取基本数据
     */
    public void doInitBaseHttp();

    /**
     * 当控件被点击时
     */
    public void onViewClick(View v);

    /**
     * 基本控件初始化
     */
    public void initWidget(View... v);

}
