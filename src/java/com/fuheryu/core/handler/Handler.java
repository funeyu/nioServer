package com.fuheryu.core.handler;

import java.nio.channels.SelectionKey;

/**
 * Created by fuheyu on 2017/7/29.
 */
public interface Handler {

    public void onRead(SelectionKey selectionKey);

}
