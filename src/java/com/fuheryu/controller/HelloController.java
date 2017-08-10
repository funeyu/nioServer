package com.fuheryu.controller;

import com.fuheryu.http.AnnotationFactory.Controller;
import com.fuheryu.http.AnnotationFactory.RouterMapping;
import com.fuheryu.http.HTTPContext;
import com.fuheryu.service.DemoService;
import com.fuheryu.service.ServiceFactory;

/**
 * Created by fuheyu on 2017/8/6.
 */
@Controller
public class HelloController {

    @RouterMapping(path="/hello", method = "get")
    public String sayHello(HTTPContext httpContext) {

        DemoService service = (DemoService) ServiceFactory.one(DemoService.class);

        return service.sayHello();
    }

}
