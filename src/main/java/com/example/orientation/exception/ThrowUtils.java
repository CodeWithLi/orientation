package com.example.orientation.exception;


/**
 * 异常工具类
 */
public class ThrowUtils {




    /**
     * 条件成立则抛异常
     *
     * @param result
     * @param message
     */
    public static void throwIf(boolean result, String message) {
        if (result){
            throw new BaseException(message);
        }
    }


    public static void serviceThrow(boolean result, String message) {
        if (result){
            throw new BaseException(message);
        }
    }




}
