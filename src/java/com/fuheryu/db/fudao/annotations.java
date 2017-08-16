package com.fuheryu.db.fudao;

import java.lang.annotation.*;

/**
 * Created by fuheyu on 2017/8/16.
 */
public class annotations {

    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Sql {
        String value();
    }

    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface FuDao {
        String value() default "";
    }

    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Arguments {
        String[] value() default {};
    }

    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface  ResultType {
        Class<?> value();
    }
}
