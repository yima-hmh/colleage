package org.example;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DH {
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
        return flag;
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
//        return b.pow(n.intValue()).mod(m);
    }

    //得到p的所有生成元
    //从2开始暴力枚举,判断g^(p-1)mod(p)=1时是否指数为p-1.
    public ArrayList<Integer> getAllRoot(int p) {
        ArrayList<Integer> list = new ArrayList<>();
        int a = 2;
        int flag = 1;
        BigInteger temp;
        while (a < p) {
            flag = 1;
            temp = BigInteger.valueOf(a);

            //这个循环主要是用来判断会不会有其他的flag使得这个式子成立.
            while (flag < p) {
                if (temp.pow(flag).mod(BigInteger.valueOf(p)).intValue() == 1) {
                    break;
                }
                flag++;
            }

            //如果是的话,出来时肯定是break出来的,此时flag来不及继续++,值就是p-1
            if (flag == p-1) {
                list.add(a);
            }
            //计算下一个a,直到a=p-1;
            a++;
        }
//        System.out.println(list);
        return list;

    }

    //判断g是不是模p成的生成元
    public boolean isPrimerRoot(int g, int p) {
        Boolean is = false;
        ArrayList list = new DH().getAllRoot(p);
//        System.out.println(list);
        for (int i = 0; i < list.size(); i++) {
            if (g == (Integer) list.get(i)) {
//                System.out.println(list.get(i));
                is = true;
                break;
            }
        }
        return is;
    }

    //获得一个素数
    public int getP() {
        DH dh = new DH();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你的公开素数q");
        int p = scanner.nextInt();
        while (dh.isPrime(p) == false) {
            System.out.println("输入错误,请输入一个素数!");
            p = dh.getP();
        }
        return p;
    }

    //获得公开的生成元
    public int getA(int p) {
        DH dh = new DH();
        Scanner scanner = new Scanner(System.in);
        System.out.println("这是你选择的素数的所有生成元:");
        System.out.println(dh.getAllRoot(p));
        System.out.println("请输入你选择的生成元");
        int g = scanner.nextInt();
        while (dh.isPrimerRoot(g, p) == false) {
            System.out.println("请输入正确的生成元");
            g = dh.getA(p);
        }
        return g;
    }


    public static void main(String[] args) {
        System.out.println("------欢迎来到DH密钥交换协议------");
        DH dh = new DH();

        int q = dh.getP();
        int a = dh.getA(q);
        Random random = new Random();

        System.out.println("-------------------------------");
        System.out.println("当前A和B都知道的q是" + q);
        System.out.println("当前A和B都知道的a是" + a);

        System.out.println("-------------------------------");
        //A生成私钥
        int XA = random.nextInt(q - 1) + 1;
        System.out.println("A的私钥是:" + XA);
        //B生成私钥
        int XB = random.nextInt(q - 1) + 1;
        System.out.println("B的私钥是:" + XB);

        System.out.println("-------------------------------");
        //A生成计算数
        BigInteger YA = dh.ExpMod(BigInteger.valueOf(a), BigInteger.valueOf(XA), BigInteger.valueOf(q));
        System.out.println("A的计算数是:" + YA);
        //B生成计算数
        BigInteger YB = dh.ExpMod(BigInteger.valueOf(a), BigInteger.valueOf(XB), BigInteger.valueOf(q));
        System.out.println("B的计算数是:" + YB);

        System.out.println("-------------------------------");
        //A生成密钥
        BigInteger KA = dh.ExpMod(YB, BigInteger.valueOf(XA), BigInteger.valueOf(q));
        System.out.println("A的生成密钥是:" + KA);
        //B生成密钥
        BigInteger KB = dh.ExpMod(YA, BigInteger.valueOf(XB), BigInteger.valueOf(q));
        System.out.println("B的生成密钥是:" + KB);
    }
}
