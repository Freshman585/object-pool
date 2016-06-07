package com.tutao.objectpool;

/**
 * Created by Aron on 16/6/7.
 */
public class CacheObject {

    private boolean isUsing;

    private Object cacheObject;

    public Object getCacheObject() {
        return cacheObject;
    }

    public void setCacheObject(Object cacheObject) {
        this.cacheObject = cacheObject;
    }

    public boolean isUsing() {
        return isUsing;
    }

    public void setUsing(boolean using) {
        isUsing = using;
    }

}
