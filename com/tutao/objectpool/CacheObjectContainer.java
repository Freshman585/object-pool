package com.tutao.objectpool;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aron on 16/6/7.
 */
public class CacheObjectContainer<T> {

    private List<CacheObject> cacheObjectList;

    private Class<T> type;

    public CacheObjectContainer(Class<T> type) {
        this.type = type;
        cacheObjectList = new ArrayList<>();
    }

    public List<CacheObject> getCacheObjectList() {
        return cacheObjectList;
    }

    public void setCacheObjectList(List<CacheObject> cacheObjectList) {
        this.cacheObjectList = cacheObjectList;
    }

    public void release(T ins) {
        for (CacheObject cacheObject : cacheObjectList) {
            if (cacheObject.getCacheObject() == ins) {
                cacheObject.setUsing(false);
                break;
            }
        }
    }

    public void destory() {
        cacheObjectList.clear();
    }

    public T getObject() {
        synchronized (this) {
            for (CacheObject cacheObject : cacheObjectList) {
                if (!cacheObject.isUsing()) {
                    return (T) cacheObject.getCacheObject();
                }
            }
            try {
                T instance = type.newInstance();
                CacheObject cacheObject = new CacheObject();
                cacheObject.setUsing(true);
                cacheObject.setCacheObject(instance);
                cacheObjectList.add(cacheObject);
                return instance;
            } catch (Exception exp) {
                Log.e(exp.getMessage(), exp.getMessage());
            }
        }
        return null;
    }

}
