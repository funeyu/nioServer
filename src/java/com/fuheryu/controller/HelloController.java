package com.fuheryu.controller;

import com.fuheryu.http.AnnotationFactory;
import com.fuheryu.http.HTTPContext;
import com.fuheryu.service.DemoService;
import com.fuheryu.service.ServiceFactory;

/**
 * Created by fuheyu on 2017/8/6.
 */
@AnnotationFactory.Controller
public class HelloController {

    @AnnotationFactory.RouterMapping(path="/hello", method = "get")
    public String sayHello(HTTPContext httpContext) {

        DemoService service = (DemoService) ServiceFactory.one(DemoService.class);

        return service.sayHello();
    }

}
