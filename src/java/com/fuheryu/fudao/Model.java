package com.fuheryu.fudao;

import java.util.HashMap;

/**
 * Created by fuheyu on 2017/8/18.
 */
public class Model {

    private HashMap<String, ColumData> rawData = new HashMap<>();

    /**
     * where的查询
     * @param query
     * @return
     */
   public static<T extends Model> LazyModelList<T> where(String query, Class<T> clazz) {

       return ModelDelegate.where(clazz, query);
   }

   public static<T extends Model> void register(Class<T> clazz) {

       ModelDelegate.register(clazz);
   }


    /**
     * 根据query查询查询某个结果
     * @param query
     * @return
     */
   public static <T extends Model> T findOne(String query, Class<T> clazz) {

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
   public static <T extends Model> boolean delete(String query, Class<T> clazz) {

       return ModelDelegate.delete(clazz, query);
   };

    /**
     * 将model存到db中
     */
   public void save() {

   };

   public HashMap<String, ColumData> rawData() {
       return rawData;
   }

}