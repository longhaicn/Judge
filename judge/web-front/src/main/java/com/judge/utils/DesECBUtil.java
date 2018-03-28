package com.judge.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesECBUtil {
    public static void main(String[] args) throws Exception {
        String sfz = "441302199111115811";//身份证
        String key = "E-7.3-V12-NO";//密钥
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dataStr = sdf.format(new Date());
        String data = sfz + key + dataStr;
        System.out.println("加密前:"+data);
        String result = encrypt(data, key);//DES加密之后，方便使用http 的get方法传参数 所以又使用了BASE64转换了一下
        result = DesECBUtil.ReplaceChars(result);//由于BASE64转换后的数据有可能包含‘=’‘+’‘/’，这些符号会造成get方法解析的错误，所以再做一下字符串替换
        System.out.println("加密后:"+result);

        //再解密
        result = DesECBUtil.DeReplaceChars(result);
        result = decrypt(result, key);
        System.out.println("解密后:"+result);
    }

    //采用DES算法进行加密，返回加密结果的Base64编码字符串,密码模式为ECB
    public static String encrypt(String message, String key) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        //IvParameterSpec iv = new IvParameterSpec("".getBytes("UTF-8"));

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] bytes = cipher.doFinal(message.getBytes("UTF-8"));
        return getBASE64(bytes);
    }

    //采用DES算法进行解密，返回加密之前的原文,密码模式为ECB
    public static String decrypt(String message,String key) throws Exception {
        byte[] bytesrc = getFromBASE64(message);

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        //IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte, "UTF-8");
    }

    //替换加密后的特殊字符
    public static String ReplaceChars(String str)
    {
        if(str == null) return str;

        String result = str.replace('+', '*');
        result = result.replace('/', '-');
        result = result.replace('=', '_');

        result = result.replaceAll("\\r\\n", "");
        result = result.replaceAll("\\n", "");

        return result;
    }
    //与ReplaceChars方法相反
    public static String DeReplaceChars(String str)
    {
        if(str == null) return str;

        String result = str.replace('*', '+');
        result = result.replace('-', '/');
        result = result.replace('_', '=');

        result = result.replaceAll("\\r\\n", "");
        result = result.replaceAll("\\n", "");

        return result;
    }

    public static String getBASE64(byte[] b) {
        String s = null;
        if (b != null) {
            try {
                Class<?> clazz = Class.forName("sun.misc.BASE64Encoder");
                Object BASE64Encoder = clazz.newInstance();
                Method encode =clazz.getMethod("encode", byte[].class);
                s = (String) encode.invoke(BASE64Encoder, b);
            } catch (Exception e) {
                e.printStackTrace();
            }
//	        s = new sun.misc.BASE64Encoder().encode(b);
        }
        return s;
    }

    public static byte[] getFromBASE64(String s) {
        byte[] b = null;
        if (s != null) {
            try {
                Class<?> clazz = Class.forName("sun.misc.BASE64Decoder");
                Object BASE64Decoder = clazz.newInstance();
                Method encode =clazz.getMethod("decodeBuffer", String.class);
                b = (byte[]) encode.invoke(BASE64Decoder, s);
            } catch (Exception e) {
                e.printStackTrace();
            }
//  	        BASE64Decoder decoder = new BASE64Decoder();
//  	        try {
//  	            b = decoder.decodeBuffer(s);
//  	            return b;
//  	        } catch (Exception e) {
//  	            e.printStackTrace();
//  	        }
        }
        return b;
    }
}

