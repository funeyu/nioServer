package com.fuheryu.fudao;

import com.fuheryu.fupool.FuPool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fuheyu on 2017/8/19.
 */
public final class ModelDelegate {

    private final static HashMap<String, Class<? extends Model>> delegates = new HashMap<>();

    private final static HashMap<Class<? extends Model>, String> models = new HashMap<>();

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

        // 执行sql
        FuPool pool = null;
        try {
            pool = FuPool.bootStrap();
            Connection connection = pool.getOne();
            Statement st = connection.createStatement();

            ResultSet result = st.executeQuery(sql.toString());
            result.next();
            if(result.wasNull()) { // 没有查到数据
                return null;
            }

            ResultSetMetaData metaData = result.getMetaData();
            int colum = metaData.getColumnCount();

            T one = (T)clazz.newInstance();
            for(int i = 1; i < colum; i ++) {
                String columName = metaData.getColumnName(i);
                String columType = metaData.getColumnTypeName(i);
                System.out.println("column type:" + columType);
                one.set(columName, ColumData.init(columName, columType, result));
            }

            pool.returnedOne(connection);
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

    /**
     * 根据查询条件 ，删除
     * @param clazz
     * @param query
     * @param <T>
     * @return
     */
    public static <T extends Model> boolean delete(Class<T> clazz, String query) {

        String name = clazz.getSimpleName().toLowerCase();

        StringBuffer sb = new StringBuffer("DELETE FROM ");
        sb.append(name).append(" WHERE ").append(query);

        FuPool pool = null;
        Connection connection = null;

        try {
            pool = FuPool.bootStrap();
            connection = pool.getOne();
            Statement st = connection.createStatement();
            st.executeUpdate(sb.toString());
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(pool != null && connection != null) {
                pool.returnedOne(connection);
            }
        }
        return false;
    }

    /**
     * 真实地处理数据insert的操作
     * @param model
     * @param <T>
     */
    public static <T extends Model> void save(T model) throws NoSuchMethodException, SQLException, InterruptedException, InvocationTargetException, IllegalAccessException {

        ArrayList<ModelField> fieldsInfo = model.collectFiledInfo();

        FuPool pool = FuPool.bootStrap();
        Connection conn = pool.getOne();
        String sql = SQL.insert(fieldsInfo, model);

        PreparedStatement pre = conn.prepareStatement(sql);



        int index = 0;
        for(ModelField field : fieldsInfo) {
            Object filedValue = field.getValue();

            ColumData.fillStatement(pre, field, ++index);
        }

        pre.executeUpdate();

        // 返回数据库连接池
        pool.returnedOne(conn);
    }

    /**
     * 根据key 得到获取key的方法名
     * 如有id，则方法名为：getId
     * @param key
     * @return
     */
    private static String getMethodName(String key) {

        return "get" + (key.substring(0,1).toUpperCase() + key.substring(1));
    }

}
