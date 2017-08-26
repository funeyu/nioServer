package com.fuheryu.fudao;

import com.fuheryu.fupool.FuPool;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by fuheyu on 2017/8/18.
 * 这个类是为了给page 分页等列表查询使用
 */
public class LazyModelList<E extends Model> extends ArrayList {

    private int offSet = 0;

    private int limit = 0;

    private String query;

    private String orderBy;

    private String sql;

    private Class<E> modelClass;

    private ArrayList<E> delegate;

    public LazyModelList(Class <E> clazz ){

        delegate = new ArrayList<E>();
        this.modelClass = clazz;
    }

    public int size() {

        if(delegate != null) {
            return delegate.size();
        }

        return 0;
    }

    /**
     * where 方法返回一个LazyModelList, 每个Class类都对应一个model
     * @param sql
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E extends Model> LazyModelList<E> where(String sql, Class<E> clazz) {

        LazyModelList lazyModelList = new LazyModelList(clazz);
        lazyModelList.query = sql;

        return lazyModelList;
    }

    public Model get(int index) {

        data();
        if(delegate != null) {
            return delegate.get(index);
        }
        return null;
    }

    /**
     * 返回该lazyModelList中的所有list的数据,
     * 如果有delegate数据，就不再去执行sql了
     * @return
     */
    public ArrayList<E> data() {

        if(!delegate.isEmpty()) {
            return delegate;
        }

        buildSQL().excuteSQL();
        return delegate;
    }

    /**
     * 将where limit sort order等组装sql语句
     * @return
     */
    private LazyModelList<E> buildSQL() {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(modelClass.getSimpleName().toLowerCase()).append(" WHERE ").append(query);
        if(orderBy != null) {
            sb.append(" ").append(orderBy);
        }
        if(limit != 0) {
            sb.append("LIMIT ").append(String.format("%d,%d", offSet, limit));
        }

        return this.setSql(sb.toString());
    }

    /**
     * 后台的查询SQL语句，拼接sql查询，进行sql查询操作
     */
    private void excuteSQL() {

        FuPool pool = null;
        try {
            pool = FuPool.bootStrap();
            Connection connection = pool.getOne();
            Statement st = connection.createStatement();

            ResultSet result = st.executeQuery(sql.toString());
            while(result.next()) {
                ResultSetMetaData metaData = result.getMetaData();
                int colum = metaData.getColumnCount();

                E one = modelClass.newInstance();
                for(int i = 1; i < colum; i ++) {
                    String columName = metaData.getColumnName(i);
                    String columType = metaData.getColumnTypeName(i);
                    one.set(columName, ColumData.init(columName, columType, result));
                }
                delegate.add(one);
            }
            pool.returnedOne(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    public LazyModelList<E> setOffSet(int offSet) {

        this.offSet = offSet;
        return this;
    }

    public LazyModelList<E> setLimit(int limit) {

        this.limit = limit;
        return this;
    }

    public LazyModelList<E> setOrderBy(String orderBy) {

        this.orderBy = orderBy;
        return this;
    }

    public LazyModelList<E> setQuery(String query) {

        this.query = query;
        return this;
    }

    private LazyModelList<E> setSql(String sql) {

        this.sql = sql;
        return this;
    }

    public int getOffSet() {
        return offSet;
    }

    public int getLimit() {
        return limit;
    }

    public String getOrderBy() {
        return orderBy;
    }
}
