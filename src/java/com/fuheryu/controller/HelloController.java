package com.fuheryu.controller;

import com.fuheryu.http.AnnotationFactory.Controller;
import com.fuheryu.http.AnnotationFactory.RouterMapping;

/**
 * Created by fuheyu on 2017/8/6.
 */
@Controller
public class HelloController {

    @RouterMapping(path="/hello", method = "get")
    public String sayHello() {
        return "hello java";
    }

}
