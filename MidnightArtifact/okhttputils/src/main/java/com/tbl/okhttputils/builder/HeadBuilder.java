package com.tbl.okhttputils.builder;

import com.tbl.okhttputils.OkHttpUtils;
import com.tbl.okhttputils.request.OtherRequest;
import com.tbl.okhttputils.request.RequestCall;

public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
