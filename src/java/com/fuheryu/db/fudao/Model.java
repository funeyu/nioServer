package com.fuheryu.db.fudao;

import java.util.HashMap;
import java.util.List;

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

   public static <T extends Model> void say(Class<T> clazz) {

       System.out.println(clazz.getSimpleName());
   }

    /**
     * 根据query查询查询某个结果
     * @param query
     * @return
     */
   public static <T extends Model> T findOne(String query, Class<T> clazz) {


       return null;
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
   public boolean delete() {

       return false;
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
