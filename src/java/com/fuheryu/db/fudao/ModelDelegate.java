package com.fuheryu.db.fudao;

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

    protected static Class<? extends Model> getModelClass(String clazzName) {
        return delegates.get(clazzName);
    }

    protected static void where(Class<? extends Model> clazz) {
        clazz.getName();
    }

    public static void get() {

    }

    public static void main(String[] args) {
        ModelDelegate.register(People.class);
    }
}
