package org.example;

import java.io.*;

public class XorUtils {
    /**
     * 实现输入一串数字也可以加解密
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] data,byte[] key){
        //data和ley随便一个为空都无法加解密
        if(data==null||data.length==0||key==null||key.length==0){
            return data;
        }
        //待会用来存结果
        byte[] result =new byte[data.length];
        //使用密钥字节数组循环加密或者解密
        for (int i = 0; i < data.length; i++) {
            //数据与密钥异或,再与循环变量的低八位异或(增加复杂度)
            result[i]=(byte)(data[i]^key[i%key.length]^(i&0xFF));
        }
        return result;
    }

    /**
     * 实现对文件的加解密
     * @param inFile
     * @param outFile
     * @param key
     * @throws IOException
     */
    public static void encryptFile(File inFile,File outFile,byte[] key)throws IOException{
        InputStream in=null;
        OutputStream out=null;

        try {
            //文件输入流
            in =new FileInputStream(inFile);
            //结果输出流,异或运算时,字节是一个一个读取和写入,这里必须使用缓冲流包装
            //登缓冲到一定数量的字节(10240字节)后再写入磁盘,否则写磁盘次数太多,速度会非常慢
            out=new BufferedOutputStream(new FileOutputStream(outFile),10240);

            int b=-1;
            long i=0;
            // 每次循环读取文件的一个字节, 使用密钥字节数组循环加密或解密
            while ((b = in.read()) != -1) {
                // 数据与密钥异或, 再与循环变量的低8位异或（增加复杂度）
                b = (b ^ key[(int) (i % key.length)] ^ (int) (i & 0xFF));
                // 写入一个加密/解密后的字节
                out.write(b);
                // 循环变量递增
                i++;
            }
            out.flush();
        } finally {
            in.close();
            out.close();
        }
    }
}
