package com.fuheryu.fudao;

import com.alibaba.fastjson.JSON;
import com.fuheryu.fupool.FuPool;

import java.sql.*;
import java.util.HashMap;

/**
 * Created by fuheyu on 2017/8/19.
 */
public final class ModelDelegate {

    private final static HashMap<String, Class<? extends Model>> delegates = new HashMap<>();

    private final static HashMap<Class<? extends Model>, String> models = new HashMap<>();

    protected static void register(Class<? extends Model> model) {

        delegates.put(model.getSimpleName(), model);
    }


    protected static <T extends Model> LazyModelList<T> where(Class<T> clazz, String query) {

        return null;
    }

    /**
     * 这个代码是一坨啊，没拆分啊， 最小功能啊
     * @param clazz
     * @param query
     * @param <T>
     * @return
     */
    public static <T extends Model> T findOne(Class<T> clazz, String query) {
        // 获取数据表名
        String name = clazz.getSimpleName().toLowerCase();

        // 组装sql
        StringBuffer sql = new StringBuffer("select * from ");
        sql.append(name).append(" where ").append(query);
        System.out.println(sql.toString());

        // 执行sql
        FuPool pool = null;
        try {
            pool = FuPool.bootStrap();
            Connection connection = pool.getOne();
            Statement st = connection.createStatement();

            ResultSet result = st.executeQuery(sql.toString());
            result.next();
            ResultSetMetaData metaData = result.getMetaData();
            int colum = metaData.getColumnCount();

            T one = (T)clazz.newInstance();
            for(int i = 1; i < colum; i ++) {
                String columName = metaData.getColumnName(i);
                String columType = metaData.getColumnTypeName(i);
                System.out.println("column type:" + columType);
                one.set(columName, ColumData.init(columName, columType, result));
            }

            return one;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


        return null;
    }

    public static void main(String[] args) {

        Seckill seckill = ModelDelegate.findOne(Seckill.class, "number = 200");
        System.out.println(JSON.toJSONString(seckill.rawData()));
    }
}
