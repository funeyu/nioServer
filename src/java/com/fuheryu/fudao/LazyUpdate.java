package com.fuheryu.fudao;

import com.fuheryu.fupool.FuPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class LazyUpdate<T extends Model> extends LazyBase {

    private ArrayList<String> attrs;

    private Class<T> clazz;

    private ArrayList<ModelField> fields;

    /**
     * 根据update 条件新建一个LazyUpdate
     * @param sql
     * @param <T>
     * @return
     */
    public static <T extends Model> LazyUpdate init(String sql, Class<T> clazz) {

        LazyUpdate<T> lazy = new LazyUpdate<>();
        lazy.sql = sql;
        lazy.clazz = clazz;
        lazy.attrs = new ArrayList<>();
        lazy.fields = new ArrayList<>();
        return lazy;
    }

    /**
     * update {tableName} set {attr} = ? update 的sql语句,其中attr就是这里的attr参数
     * @param attr
     * @param o
     */
    public LazyUpdate<T> with(String attr, Object o) {

        attrs.add(attr);
        ModelField field = ModelField.init(attr, ColumData.filedType(clazz, attr), o);
        fields.add(field);

        return this;
    }


    @Override
    LazyBase executeSQL() {

        String sql = buildSQL();
        FuPool pool = null;
        Connection conn = null;
        try {
            pool = FuPool.bootStrap();
            conn = pool.getOne();
            PreparedStatement statement = conn.prepareStatement(sql);

            int index = 0;
            for(ModelField field : fields) {
                Object filedValue = field.getValue();

                ColumData.fillStatement(statement, field, ++index);
            }

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(conn != null && pool != null) {
                pool.returnedOne(conn);
            }
        }
        return this;
    }

    @Override
    String buildSQL() {
        StringBuffer sb = SQL.update(fields, clazz);
        sb.append(" WHERE ").append(sql);

        return sb.toString();
    }

    public static void main(String[] args) {

        LazyUpdate<Seckill> lazyUpdate = LazyUpdate.init("seckill_id = 1000", Seckill.class);
        lazyUpdate.with("number", 10000).executeSQL();
    }
}
