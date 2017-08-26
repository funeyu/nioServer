package com.fuheryu.fudao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        // 保存的示例
        Seckill s = new Seckill(
                "test",
                988,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new BigInteger("18903002")
        );
        s.save();

        // 修改接口的示例
        Seckill.update("number = 10000", Seckill.class)
                .with("number", 989)
                .executeSQL();

        // 删除接口的示例
        Seckill.delete(Seckill.class, "number = 200");

        // 查找的接口示例
        Seckill seckill = Seckill.findOne(Seckill.class, "number = 300");

        // 分页查询
        ArrayList<Seckill> seckills =
                Seckill.where(Seckill.class, "number = 400").limit(10).offSet(0).data();
        System.out.println(seckills.size());

    }
}
