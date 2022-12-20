package org.example;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("-------欢迎来到文件加解密程序:-------");
        System.out.println("请输入你要进行的操作:");
        System.out.println("1.加密    2.解密    ");
        Scanner select = new Scanner(System.in);
        int select1 = select.nextInt();

        if(select1==1) {
            System.out.println("请输入你要加密的文件所在的绝对路径地址:");;
        }
        else if(select1==2){
            System.out.println("请输入你要解密的文件所在的绝对路径地址:");
        }
        else{
            System.out.println("请输入正确的选项");
            main(null);
        }
        /*输入文件的地址*/
        Scanner inFile1 = new Scanner(System.in);
        String inFlieWay = inFile1.nextLine();
        /*输出文件的地址*/
        System.out.println("请输入输出文件的绝对路径地址");
        Scanner outFile1 = new Scanner(System.in);
        String outFlieWay = outFile1.nextLine();
        File inFile = new File(inFlieWay);
        File outFile = new File(outFlieWay);

        /*密钥*/
        System.out.println("请输入你的密钥:");
        Scanner scanner = new Scanner(System.in);
        String key = scanner.nextLine();
        //得到字节数组
        byte[] bytekey=key.getBytes();

        XorUtils.encryptFile(inFile,outFile,bytekey);
    }

}
