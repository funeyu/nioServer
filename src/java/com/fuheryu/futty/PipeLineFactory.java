package com.fuheryu.futty;

/**
 * Created by fuheyu on 2018/4/23.
 */
public class PipeLineFactory {

    public static ChannelPipeLine pipeLine() {
        return new DefaultPipeLine();
    }

}
