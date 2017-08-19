package com.fuheryu.db.fudao;

/**
 * Created by fuheyu on 2017/8/18.
 */
public class Seckill extends Model {
    static {
        register(Seckill.class);
    }

    public static Class<Seckill> modelClass() {

        return Seckill.class;
    }

    public static void main(String[] args) {

        LazyModelList<Seckill> ps = Seckill.<Seckill>where("java", Seckill.class);

    }
}
