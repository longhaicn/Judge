package com.judge.utils;

/*
 * 定义状态码
 */
public class StatusCode {

    public static String CODE_SUCCESS = "0";          //访问成功

    public static String CODE_ERROR = "1";          //访问错误

    public static String CODE_ERROR_PARAMETER = "002";//参数错误

    public static String CODE_ERROR_PROGRAM = "003";  //程序异常

    public static String CODE_ERROR_NO_LOGIN_OR_TIMEOUT = "004";  //未登录或登录超时,请重新登录

    public static String CODE_ERROR_EXIST_OPERATION = "005";      //已操作

}
