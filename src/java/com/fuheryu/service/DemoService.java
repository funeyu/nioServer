package com.fuheryu.service;

import com.alibaba.fastjson.JSON;
import com.fuheryu.fudao.Seckill;

/**
 * Created by fuheyu on 2017/8/10.
 */
public class DemoService {

    public String queryByNumber(int number) {

        Seckill s = Seckill.findOne("number = " + number, Seckill.class);
        return JSON.toJSONString(s.rawData());
    }
}
