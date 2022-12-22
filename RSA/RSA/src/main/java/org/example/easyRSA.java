package org.example;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class easyRSA {
    // 判断n是否是素数
    public boolean isPrime(int n) {
        boolean flag = true;
        //求平方根
        int max = (int) Math.sqrt(n);
        for (int i = 2; i <= max; i++) {
            if (n % i == 0) {
                flag = false;
                break;
            }
        }
        //排除1
        if(n!=1){
            return flag;
        }else return !flag;
    }

    //计算a,b的最大公因子
    public int GCD(int a, int b) {
        int gcd = 0, c;
        if (a > b) {
            while (b != 0) {
                a = a % b;
                if (a < b) {
                    c = a;
                    a = b;
                    b = c;
                }
                gcd = a;
            }
        }
        if (a == b)
            gcd = a;
        else {
            c = a;
            a = b;
            b = c;
            while (b != 0) {
                a = a % b;
                if (a < b) {
                    c = a;
                    a = b;
                    b = c;
                }
                gcd = a;
            }
        }
        return gcd;
    }

    //计算b^n mod m
    public BigInteger ExpMod(BigInteger b, BigInteger n, BigInteger m) {
        BigInteger a = new BigInteger("1");
        //进制转换
        String t1 = n.toString(2);
        int[] N = new int[t1.length()];
        for (int i = 0; i <= t1.length() - 1; i++) {
            N[i] = Integer.parseInt(t1.substring(t1.length() - i - 1, t1.length() - i));
        }

        for (int j = 0; j <= t1.length() - 1; j++) {

            if (j != t1.length() - 1) {
                if (N[j] == 1)
                    a = (a.multiply(b)).remainder(m);
                if (N[j] == 0)
                    a = a.remainder(m);
                b = (b.multiply(b)).remainder(m);
            } else {
                if (N[j] == 1)
                    a = (a.multiply(b)).remainder(m);
                if (N[j] == 0)
                    a = a.remainder(m);
            }
        }
        return a;
    }


    //获得序号为index的素数
    public int getP(int index) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入第" + index + "个公开素数:");
        int p = scanner.nextInt();
        while (isPrime(p) == false) {
            System.out.println("输入错误,请输入一个素数!");
            p = getP(index);
        }
        return p;
    }

    //得到e的集合
    public ArrayList<Integer> getE(int p, int q) {
        ArrayList<Integer> list = new ArrayList<>();

        Integer fineN = getfineN(p, q);
        int tempE = 2;
        while (tempE != fineN) {
            if (GCD(fineN, tempE) == 1) {
                list.add(tempE);
            }
            tempE++;
        }
        return list;
    }

    //得到n
    public Integer getN(int p, int q) {
        return p * q;
    }

    //得到欧拉n
    public Integer getfineN(int p, int q) {
        return (p - 1) * (q - 1);
    }

    //让用户选择公钥
    public Integer selectE(int p, int q) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("---------------------------------");
        System.out.println("你选择的两个素数是:" + p + "," + q);
        System.out.println("两个素数可以生成的公钥e集合如下:");
        System.out.println(getE(p, q));
        System.out.println("请选择你随机选择的公钥:");
        int select = scanner.nextInt();
        return select;
    }

    //让用户输入明文
    public Integer getPlainText() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要加密的明文(数字):");
        Integer plainText = scanner.nextInt();
        while (plainText.equals(null)) {
            plainText=getPlainText();
        }
        return plainText;
    }

    //得到私钥
    public Integer getPrid(int e, int fineN) {
        //思路是先生成一个包含着
        Random random = new Random();
        ArrayList<Integer> list = new ArrayList<>();
        int tempD = 2;
        BigInteger useE = BigInteger.valueOf(e);
        BigInteger useN = BigInteger.valueOf(fineN);
        while (tempD != fineN) {
            if (BigInteger.valueOf(tempD).multiply(useE).mod(useN).intValue() == 1) {
                list.add(tempD);
            }
            tempD++;
        }
        if (!list.isEmpty()) {
            int randomInt = random.nextInt(list.size());
            return list.get(randomInt);
        } else {
            return null;
        }
    }


    //公钥加密
    public BigInteger Encrypt(int plainText, int e, int n) {
        BigInteger mod = ExpMod(BigInteger.valueOf(plainText), BigInteger.valueOf(e), BigInteger.valueOf(n));
        System.out.println("公钥加密之后密文如下: ");
        System.out.println(mod.intValue());
        return mod;
    }


    //解密
    public BigInteger Decrypt(int encryptTetx, int e, int n) {
        BigInteger mod = ExpMod(BigInteger.valueOf(encryptTetx), BigInteger.valueOf(e), BigInteger.valueOf(n));
        System.out.println("私钥解密之后明文如下: ");
        System.out.println(mod.intValue());
        return mod;
    }


    public static void main(String[] args) {
        System.out.println("------欢迎来到RSA简单程序------");
        easyRSA easyrsa = new easyRSA();
        //输入两个素数
        int p = easyrsa.getP(1);
        int q = easyrsa.getP(2);
        //输出公钥让用户选择
        Integer e = easyrsa.selectE(p, q);
        //获取私钥
        Integer fineN = easyrsa.getfineN(p, q);
        Integer d = easyrsa.getPrid(e, fineN);
        System.out.println("你所得到的私钥是:"+d);

        //请用户输入明文
        int plainText = easyrsa.getPlainText();

        //公钥加密
        Integer n = easyrsa.getN(p, q);
        BigInteger encryptText = easyrsa.Encrypt(plainText, e, n);

        //私钥解密
        BigInteger decryptText = easyrsa.Decrypt(encryptText.intValue(), d, n);
    }
}