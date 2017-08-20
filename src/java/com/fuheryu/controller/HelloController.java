package com.fuheryu.controller;

import com.fuheryu.core.annotation.AnnotationFactory;
import com.fuheryu.core.http.Context;
import com.fuheryu.service.DemoService;
import com.fuheryu.core.ServiceFactory;

/**
 * Created by fuheyu on 2017/8/6.
 */
@AnnotationFactory.Controller
public class HelloController {

    @AnnotationFactory.RouterMapping(path="/hello", method = "get")
    public String sayHello(Context context) {

        DemoService service = (DemoService) ServiceFactory.one(DemoService.class);

        return service.queryByNumber(200);
    }

    @AnnotationFactory.RouterMapping(path="/newPost", method = "post")
    public String createPost(Context context) {

        return "success";
    }
}
