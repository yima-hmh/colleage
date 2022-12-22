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
    public static void main(String ager[]) {
        hashFine hashFine = new hashFine();

        //查找的目录,假设是C:\Users\yima\Desktop\ns\Hash
        File file1 =hashFine.getFile();
        HashMap<String, String> hashMap = new HashMap();     //创建一个hashMap存放文件路径和它的MD5值

        getfiles(file1, hashMap);
//        System.out.println(hashMap);
        Set<Map.Entry<String, String>> set = hashMap.entrySet();//set存放hashmap的一对一队的set元素

        ArrayList<String> array = new ArrayList<>();  //array存放MD5值,值可以重复
        for (Map.Entry<String, String> map : set) {
            array.add(map.getValue());
        }
        array.sort(new hashComparator());   //array按MD5排序,array.getKey表示获取键值,array.getValue表示获取获取键对应的MD5值

        ////存放文件路径,这里使用list是因为list天生无重复的,适合存重复文件的路径
        List<String> keyList = new ArrayList<>();//使用keylist存放文件路径,值不能重复.
        int count = 1;
        System.out.println("相同文件为(以横杠--区分每一批):");

        for (int i = 1; i < array.size(); i++) {
            if (array.get(i).equals(array.get(i - 1))) {
                count++;
            } else if (count > 1) {
                //获取所有MD5值相同的文件的文件路径
                //遍历多有set的元素,如果刚刚那个相同元素MD5值在set中有任何相同的,都加入到路径集合之中
                for (Map.Entry<String, String> entry : set) {
                    if (array.get(i - 1).equals(entry.getValue())) {
                        keyList.add(entry.getKey());
                    }
                }
//                System.out.println(keyList);
                //遍历keyList
                for(String list:keyList){
                    System.out.println(list);
                }
                System.out.println("--------------------");
                keyList.clear();//reset the keylist
                count = 1;     //reset the count
            }
        }

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