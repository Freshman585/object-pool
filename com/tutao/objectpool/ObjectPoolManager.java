package com.tutao.objectpool;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Aron on 16/6/7.
 */
public class ObjectPoolManager {

    private static final ObjectPoolManager _defaultManager = new ObjectPoolManager();

    public static ObjectPoolManager getDefault() {
        return _defaultManager;
    }

    private HashMap<String, CacheObjectContainer> cacheObjectHash;

    public ObjectPoolManager() {
        cacheObjectHash = new HashMap<>();
    }

    public <T> T getObject(Class<T> classType) {
        T instance = null;
        synchronized (this) {
            try {
                instance = (T) classType.newInstance();
                String className = instance.getClass().getName();
                if (cacheObjectHash.containsKey(className)) {
                    CacheObjectContainer container = cacheObjectHash.get(className);
                    return (T) container.getObject();
                } else {
                    CacheObjectContainer container = new CacheObjectContainer(classType);
                    cacheObjectHash.put(className, container);
                    return (T) container.getObject();
                }
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
            }
        }
        return instance;
    }

    public <T> void release(T ins) {
        String className = ins.getClass().getName();
        if (cacheObjectHash.containsKey(className)) {
            CacheObjectContainer container = cacheObjectHash.get(className);
            container.release(ins);
        }
    }

    public void destory() {
        Iterator it = cacheObjectHash.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            cacheObjectHash.get(key).destory();
        }
    }

    public <T> void destory(T type) {
        String className = type.getClass().getName();
        if (cacheObjectHash.containsKey(className)) {
            CacheObjectContainer container = cacheObjectHash.get(className);
            container.destory();
        }
    }

    public static void main(String[] args) {
        Object ins = ObjectPoolManager.getDefault().getObject(ObjectPoolManager.class);
        int hello = 1010;
        hello++;
        ObjectPoolManager.getDefault().release(ins);

        ins = ObjectPoolManager.getDefault().getObject(ObjectPoolManager.class);
        hello++;
        ObjectPoolManager.getDefault().release(ins);
    }

}
