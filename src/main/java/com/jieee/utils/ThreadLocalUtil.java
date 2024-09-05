package com.jieee.utils;

@SuppressWarnings("all")
public class ThreadLocalUtil {
    //提供ThreadLocal对象,全局唯一
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    //根据键获取值
    //使用泛型t用于强转任意类型 因为threadlocal支持所有类型
    public static <T> T get(){
        return (T) THREAD_LOCAL.get();
    }

    //存储键值对
    public static void set(Object value){
        THREAD_LOCAL.set(value);
    }

    //清除ThreadLocal 防止内存溢出
    //它的生命周期长
    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
