package org.example;


import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.Base64;

public class TestApp {
    @Test
    public void toBinary(){
        MD5 md5 = new MD5();
        String str = "123zheng郑";
        char[] strChar=str.toCharArray();
        System.out.println(strChar);
        String result="";
        for(int i=0;i<strChar.length;i++){
            result +=Integer.toBinaryString(strChar[i])+ " ";
        }
        System.out.println(result);
    }

    @Test
    public void TestStringMD5() throws UnsupportedEncodingException {
  /*      String md5 = MD5.getMD5("郑光宇123");
        char[] strChar = md5.toCharArray();
        String result="";

        for(int i=0;i<strChar.length;i++){
            result +=Integer.toBinaryString(strChar[i])+ " ";
        }
        System.out.println(strChar.length);
        System.out.println(result);

        System.out.println("--------------------");
        System.out.println(Integer.toBinaryString(strChar[0]));;
        System.out.println("--------------------");

        System.out.println(md5);
//        System.out.println(md5.getBytes());
        String md52 = MD5.getMD5("郑光宇123");
        String md53 = MD5.getMD5("郑光宇123");
        System.out.println(md52);
        System.out.println(md53);
        byte a=127;
        System.out.println(a);
        a++;
        System.out.println(a);
        String b="郑光宇123";
        byte[] bytes = b.getBytes();
        System.out.println(bytes);*/
        System.out.println(MD5.getMD5("zhenggguangyu123"));
        System.out.println(MD5.getMD5("zhenggguangyu123"));
        System.out.println(MD5.getMD5("郑光宇123456啊啊啊啊"));
        System.out.println(MD5.getMD5("郑光宇123456啊啊啊啊"));
        System.out.println(MD5.getMD5("shaishaiushakhasjksakcxskjcbs"));
        System.out.println(MD5.getMD5("shaishaiushakhasjksakcxskjcbs"));
        System.out.println("------");
        String s1 = MD5.getMD5("zhenggguangyu123");
        String s2 = MD5.getMD5("zhenggguangyu123");

        System.out.println(s1);
        System.out.println(s2);
        //这里输出不一样是因为输出的是地址
        byte[] bytes = s1.getBytes("UTF-8");
        System.out.println(s2.getBytes("UTF-8"));
    }


}