最近的Android项目里面需要频繁的添加/删除元素，导致了对象的频繁创建且难以管理，所以抽空实现了对象池技术，使用起来很简单。

    一共就三个类，CacheObject,CacheObjectContainer,CacheObjectManager。

    CacheObject用来包装真实的对象，里面有一个布尔值，代表该对象是否被使用中。

    CacheObjectContainer本质上就是缓存某种类型的对象列表。

    CacheObjectManager提供外部接口，所有的获取对象，释放对象，销毁对象的接口都在该类中实现。

    简单介绍下使用方式:

    比如：

    class A{}

    class B{}

    我们获取A的对象时，直接使用CacheObjectManager.getDefault().getObject(A.class)即可获取到对象，不用自己创建。  程序内部会自动检索是否有空闲的对象，如果有空闲的对象就返回空闲的对象，否则就创建一个新的对象返回。

CacheObjectManager.getDefault().getObject(A.class)
    不用的时候，使用CacheObjectManager.getDefault().release(a)即可把对象还给对象池。被返回的对象就是空闲的了，供下一次使用。

CacheObjectManager.getDefault().release(a)
    确定以后不再使用时，可以使用

CacheObjectManager.getDefault().destory(A.class)
即可删除所有A类型的对象缓存。