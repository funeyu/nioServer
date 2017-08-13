package com.fuheryu.core.parser.header;

import com.fuheryu.core.http.Context;

import java.util.Map;


/**
 * Created by fuheyu on 2017/8/12.
 */
public abstract class ParserBase {

    public ParserBase next;

    public ParserBase(ParserBase next) {
        this.next = next;
    }

    public void next(Context context, HeaderMap headerMap) {
        if(next != null) {
            next.parse(context, headerMap);

        } else {
            // 结束cookie url accept accept-language accept-encoding的解析
            // 直接存储key value的形式到context.req attributes属性上
            for(Map.Entry<String, String> entry : headerMap.entrySet()) {
                context.getReq().setAttribute(entry.getKey(), entry.getValue());
            }
        }
    }

    public abstract void parse(Context context, HeaderMap headerMap);
}
