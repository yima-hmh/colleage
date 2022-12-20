package org.example;

import org.example.utils.Matrix_c;

import java.util.*;

public class Hill{
    Character[] x = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    //创建映射表
    HashMap<Character,Integer> wordMap = new HashMap();
    HashMap<Integer,Character> numMap = new HashMap();

    public Hill(){
        //根据其索引找值
        for(int i = 0; i < x.length; i++){
            numMap.put(i,x[i]);
        }
        //根据值得到其索引
        for(int i = 0; i < x.length; i++){
            wordMap.put(x[i],i);
        }
    }

    //获取密钥,是个矩阵,也就是二维数组
    public int[][] getSecretKey(){
        ArrayList<String> keyArray = new ArrayList<String>();
        Scanner scanKey = new Scanner(System.in);
        System.out.println("please input a N x N matrix secret key(input x to finish input): ");
        String nextLine = scanKey.nextLine();
        while (!nextLine.equals("x")){
            keyArray.add(nextLine);
            nextLine = scanKey.nextLine();
        }
        System.out.println(keyArray);
        int arrarySize = keyArray.size();
        int [][] secretKey = new int[arrarySize][arrarySize];
        for (int i = 0 ; i < arrarySize; i++){
            //取集合某一部分的值出来,然后用空格区分取值,然后vec集合就是spring数组,化成整数数组元素,存进密钥矩阵之中.
            String[] vec = keyArray.get(i).split(" ");
            for (int j = 0; j < arrarySize; j++){
                secretKey[i][j] = Integer.valueOf(vec[j]).intValue();
            }
        }
        return secretKey;
    }

    //获取密文或明文
    public String getText(){
        Scanner scanText = new Scanner(System.in);
        String text = new String();
        System.out.println("please input the text you want to encrypt or decrypt:");
        String nextLine = scanText.nextLine();
        while (nextLine != null && !nextLine.equals("")){
            text+=nextLine;
            nextLine = scanText.nextLine();
        }
        text=text.replaceAll(" ","").toLowerCase();

        return text;
    }

    //加密
    public void Encrypt(String cleartext, Matrix_c secretKey){
        String cipherText = "";
        int splitLen = secretKey.len;            //分割长度
        int textLen = cleartext.length();       //明文长度
        int addLen = 0;
        if (textLen % splitLen != 0){
            addLen = splitLen - textLen % splitLen;            //需要补齐的长度,补上a
        }
        int splitTextLen = (textLen+addLen)/splitLen;
        String[] splitText = new String[splitTextLen];
        for (int i = 0; i < addLen; i++){
            cleartext+='a';
        }
        for (int i = 0,j = 0; i <= textLen+addLen-splitLen; i=i+splitLen,j++){
            splitText[j] = cleartext.substring(i,i+splitLen);
        }

        Character[][] splitArray = new Character[splitTextLen][splitLen];

        for (int i = 0; i < splitText.length; i++){                     //转为字符数组
            for (int j = 0; j < splitLen; j++){
                splitArray[i][j] = splitText[i].charAt(j);
            }
        }

        int[][] splitArrayToInt = new int[splitTextLen][splitLen];    //将字符转为对应数字
        for (int i = 0; i < splitText.length; i++){
            for (int j = 0; j < splitLen; j++){
                splitArrayToInt[i][j] = wordMap.get(splitArray[i][j]);
            }
        }

        int[][] cipherArray = secretKey.MultplyMatrix(splitArrayToInt); //数字矩阵乘以密钥矩阵加密
        for (int[] i: cipherArray){
            for (int j : i){
                cipherText = cipherText + numMap.get(j%26) +" ";
            }
        }
        System.out.println(cipherText.replaceAll(" ",""));
    }

    public static void main(String[] args) {
        System.out.println("Please enter number 1 or 2 to choose which operation you want to do (1.Encrypt 2.Decrypt): ");
        Scanner s = new Scanner(System.in);
        int flag = s.nextInt();

        if(flag != 1 && flag != 2)
        {
            System.out.println("Please enter the correct number!");
            System.exit(0);
        }

        //gaul is divided into three parts
        //JDXO LV GLYLGHG LQWR WKUHH SDUWV
        Hill c = new Hill();
        int[][] secretKey = c.getSecretKey();
        Matrix_c matrix_c = new Matrix_c();
        matrix_c.setValue(secretKey);
        String text = c.getText();
        if(flag == 1) c.Encrypt(text,matrix_c);
        else if(flag == 2) c.Decrypt(text,matrix_c);
        s.close();
    }
}

