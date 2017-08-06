package com.fuheryu.controller;

import com.alibaba.fastjson.JSON;
import com.fuheryu.http.AnnotationFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fuheyu on 2017/8/6.
 */

@AnnotationFactory.Controller
public class JsonController {

    @AnnotationFactory.RouterMapping(path="/helloJson", method = "get")
    public String test() {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", "java");
        ArrayList<String> arrays = new ArrayList<String>();
        arrays.add("name");
        arrays.add("funer");
        map.put("list", arrays);

        return JSON.toJSONString(map);
    }
}
