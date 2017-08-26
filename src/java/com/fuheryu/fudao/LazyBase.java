package com.fuheryu.fudao;

public abstract class LazyBase<T extends Model> {
    String sql;

    /**
     * 执行sql语句
     * @return
     */
    abstract LazyBase executeSQL();

    /**
     * 拼接sql语句
     * @return
     */
    abstract String buildSQL();

}
