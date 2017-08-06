package com.fuheryu.http;

import java.lang.annotation.*;

/**
 * Created by fuheyu on 2017/8/6.
 */
public class AnnotationFactory {
    /**
     * Created by fuheyu on 2017/8/1.
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Controller {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface RouterMapping {

        String path() default "";

        String method() default "get";
    }
}
