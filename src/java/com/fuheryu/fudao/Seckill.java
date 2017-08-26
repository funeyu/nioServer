package com.fuheryu.fudao;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by fuheyu on 2017/8/18.
 */
public class Seckill extends Model {

    private String name;

    private int number;

    private Timestamp start_time;

    private Timestamp end_time;

    private Timestamp create_time;

    private BigInteger seckill_id;


    public Seckill(String name, int number, Timestamp start_time, Timestamp end_time, Timestamp create_time, BigInteger seckill_id) {

        this.name = name;
        this.number = number;
        this.end_time = end_time;
        this.create_time = create_time;
        this.seckill_id = seckill_id;
    }

    @Override
    public String[] organize() {

        return new String[]{"name", "number", "start_time", "end_time", "create_time", "seckill_id"};
    }

    @Override
    public ArrayList<ModelField> collectFiledInfo() {

        return ColumData.extractFieldData(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public void setSeckill_id(BigInteger seckill_id) {
        this.seckill_id = seckill_id;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public BigInteger getSeckill_id() {
        return seckill_id;
    }

}
