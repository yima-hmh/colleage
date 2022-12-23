package org.example;

import java.io.File;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

public class MAIN {
    //询问程序
    public void ASK(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("-----------------------------------------------------");
            System.out.println("以选项前的数字为代表,请输入你要进行的操作:");
            System.out.println("1.对字符串进行签名验证"+"   2.对文件进行签名验证"+"    3.退出程序  ");
            int select = scanner.nextInt();
            switch (select){
                case 1:
                    simulateString();
                    break;
                case 2:
                    simulateFile();
                    break;
                case 3:
                    System.out.println("--------欢迎下次使用!!--------");
                    System.exit(0);
            }
        }
    }

    //向用户获取字符串
    public String getUserString(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你要进行签名的字符串:");
        String line = scanner.nextLine();
        while (line==null||line==""){
            System.out.println("未检测到字符串");
            line=getUserString();
        }
        return line;
    }
    //

    //模拟字符串签名过程
    public void simulateString(){
        //首先,rsa算法需要一个密钥对
        try {
            KeyPair keyPair = RSA.generateKeyPair();
            //获取私钥
            PrivateKey privateKey = keyPair.getPrivate();
            //获取公钥
            PublicKey publicKey = keyPair.getPublic();
            //向用户获取输入的字符串
            String string = getUserString();
            //得到字符串的MD5
            String md5 = MD5.getMD5(string);
            System.out.println("字符串得到的MD5值是"+md5);
            //私钥签名
            byte[] priSign = RSA.sign(string.getBytes(), privateKey);
//            System.out.println("得到的的私钥是签名是"+priSign.toString());
            //公钥验签
            boolean verify = RSA.verify(string.getBytes(), priSign, publicKey);
            if(verify==true){
                System.out.println("公钥验签之后的判断是:"+"验签成功,签名有效");
            }else {
                System.out.println("签名不可信!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试用户输入的是不是一个文件的路径
    public boolean isFile(String fileRoute){
        File file = new File(fileRoute);
        if(file.isFile()&&file.exists())
            return false;
        else return true;
    }
    //用户输入要判断的文件路径
    public String getFileString(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你要进行签名的文件路径:");
        String line = scanner.nextLine();
        while (line==null||line==""||isFile(line)){
            System.out.println("未检测到文件路径或者您输入的不是一个文件的路径而是一个文件夹的路径,请重新输入");
            line=getFileString();
        }
        return line;
    }

    //模拟文件
    public void simulateFile(){
        //首先,rsa算法需要一个密钥对
        try {
            KeyPair keyPair = RSA.generateKeyPair();
            //获取私钥
            PrivateKey privateKey = keyPair.getPrivate();
            //获取公钥
            PublicKey publicKey = keyPair.getPublic();
            //向用户获取文件路径
            String string = getFileString();
            File file = new File(string);
            //得到字符串的MD5
            String md5 = MD5.getMD5(string);
            System.out.println("文件得到的MD5值是"+md5);
            //私钥签名
            byte[] priSign = RSA.sign(md5.getBytes(), privateKey);
            //公钥验签,传入文件的md5值,私钥签名,然后公钥解密
            boolean verify = RSA.verify(md5.getBytes(), priSign, publicKey);
            if(verify==true){
                System.out.println("公钥验签之后的判断是:"+"验签成功,签名有效");
            }else {
                System.out.println("签名不可信!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }





    public static void main(String[] args) {
        System.out.println("------欢迎来到模拟数字签名的程序------");
        System.out.println("------本程序模拟使用使用RSA算法对字符串或者文件的HASH值进行签名-------");
        new MAIN().ASK();
    }
}
