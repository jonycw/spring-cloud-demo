package com.mint.other.util;


/**
 * @author cw
 */
public class MyThreadLocal {

    private static ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    /**
     * 设置线程数据user
     * @param user 缓存数据
     */
    public static void set(Object user){
        threadLocal.set(user);
    }

    /**
     * 获取线程数据
     * @return User
     */
    public static Object get(){
        return threadLocal.get();
    }

    /**
     * 删除线程数据
     */
    public static void remove(){
        threadLocal.remove();
    }
}
