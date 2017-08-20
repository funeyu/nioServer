package com.fuheryu.fudao;

import java.util.ArrayList;

/**
 * Created by fuheyu on 2017/8/18.
 */
public class LazyModelList<E extends Model> extends ArrayList {
    private int offSet;

    private int limit;

    private String query;

    private String orderBy;

    private ArrayList<E> delegate;

    public LazyModelList(){
        delegate = new ArrayList<E>();
    }

    public Model get(int index) {

        return null;
    }

    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
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
