package com.fuheryu.db.fudao;

/**
 * Created by fuheyu on 2017/8/18.
 */
public class People extends Model {
    static {
        People.register(People.class);
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public void save() {

    }

    public static  Class<People> modelClass() {

        return People.class;
    }

    public static void main(String[] args) {
        LazyModelList<People> ps = People.<People>where("java");

    }
}
