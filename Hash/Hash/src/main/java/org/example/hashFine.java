package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class hashFine {
    //向用户询问查找的目录是哪个
    public File getFile(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你要对比的磁盘路径:");
        String line = scanner.nextLine();
        while (line==null){

        }
        File file = new File(line);
        return file;
    }

    //实现初始化hashmap,其值不用返回,这里改变之后,在main方法中的hashmap就已经改变,有递归的思想在这里.
    public static void getfiles(File file, HashMap hashMap) {  //保存文件路径及MD5
        if (file.isFile()) {
            String fileName = file.getAbsolutePath();
            String hashCode = getFileMD5(file);
            hashMap.put(fileName, hashCode);
            return;
        }
        File[] files = file.listFiles();
        for (File file1 : files) {
            getfiles(file1, hashMap);
        }
    }


    public static String getFileMD5(File file) {      //获取MD5
        BigInteger bigInt = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            //使用java类
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            bigInt = new BigInteger(1, md.digest());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bigInt.toString(16);
    }

}

class hashComparator implements Comparator<String> {   //比较器
    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
}