package com.fuheryu.db.fudao;

import javax.jws.WebParam;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fuheyu on 2017/8/18.
 */
public class Model {

    private HashMap<String, String> rawData = new HashMap<>();

    /**
     * where的查询
     * @param query
     * @return
     */
   public static<T extends Model> LazyModelList<T> where(String query) {


       ModelDelegate.where(Model.<T>modelClass());
       return null;
   }

   protected static <T extends Model> Class<T> modelClass() {

       return T.modelClass();
   }

   public static<T extends Model> void register(Class<T> clazz) {
        ModelDelegate.register(clazz);
   }

   public static <E extends Model> void say(Class<E> clazz) {
        System.out.println(clazz.getSimpleName());
   }

   public static List<? extends Model> execSQL() {

       return null;
   }

    /**
     * 根据query查询查询某个结果
     * @param query
     * @return
     */
   public static Model findOne(String query) {

       return null;
   }

    /**
     * 给某个Model设置key的属性值为value
     * @param key
     * @param value
     * @return
     */
   public Model set(String key, String value) {
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


}
