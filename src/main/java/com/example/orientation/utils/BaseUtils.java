package com.example.orientation.utils;


/**
 * 线程用于存放用户的学号，便于后期使用
 */
public class BaseUtils {


    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();


    public static void setCurrentAccount(String account) {threadLocal.set(account);}


    public static String getCurrentAccount() {
        return threadLocal.get();
    }


    public static void removeCurrentAccount() {
        threadLocal.remove();
    }


}
