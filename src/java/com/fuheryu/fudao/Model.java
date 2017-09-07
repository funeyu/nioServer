package com.fuheryu.fudao;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fuheyu on 2017/8/18.
 */
public abstract class Model {

    private HashMap<String, ColumData> rawData = new HashMap<>();


    /**
     * Model下的字段整理,返回一个string 数组
     * 如：
     *  A extends Model {
     *      public String name;
     *      public String sex;
     *  }
     *  就返回为['name', 'sex']
     * @return
     */
    public abstract String[] organize();

    public abstract ArrayList<ModelField> collectFiledInfo();
    /**
     * where的查询
     * @param query
     * @return
     */
   public static<T extends Model> LazyModelList<T> where(Class<T> clazz, String query) {

       return LazyModelList.where(query, clazz);
   }

    /**
     * 根据query查询查询某个结果
     * @param query
     * @return
     */
   public static <T extends Model> T findOne(Class<T> clazz, String query) {


       return ModelDelegate.findOne(clazz, query);
   }

    /**
     * 给某个Model设置key的属性值为value
     * @param key
     * @param value
     * @return
     */
   public Model set(String key, ColumData value) {

       rawData.put(key, value);
       return this;
   };

    /**
     * 删除
     * @return
     */
   public static <T extends Model> boolean delete(Class<T> clazz, String query) {

       return ModelDelegate.delete(clazz, query);
   };

    /**
     * 将model存到db中
     */
   public  <T extends Model> void save() {

       try {
           ModelDelegate.save(this);

       } catch (NoSuchMethodException e) {
           e.printStackTrace();
       } catch (SQLException e) {
           e.printStackTrace();
       } catch (InterruptedException e) {
           e.printStackTrace();
       } catch (InvocationTargetException e) {
           e.printStackTrace();
       } catch (IllegalAccessException e) {
           e.printStackTrace();
       }
   };

   public static <T extends Model>LazyUpdate<T> update(String where, Class<T> clazz) {

       return LazyUpdate.init(where, clazz);
   }

   public HashMap<String, ColumData> rawData() {
       return rawData;
   }

}
