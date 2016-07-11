package com.d.dao.eventbus_learning;

/**
 * Created by dao on 7/11/16.
 */
public class MainMessage {
    String msg;

    public MainMessage(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
