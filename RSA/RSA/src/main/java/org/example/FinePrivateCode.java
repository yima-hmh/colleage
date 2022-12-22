package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class FinePrivateCode {
    //请求用户输入获取公钥e
    public Integer getE() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入已知的公钥E:");
        Integer anInt = scanner.nextInt();
        while (anInt.equals(null)) {
            anInt = getE();
        }
        return anInt;
    }

    //请求用户输入乘积n
    public Integer getN() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入已知的乘积N:");
        Integer anInt = scanner.nextInt();
        while (anInt.equals(null)) {
            anInt = getE();
        }
        return anInt;
    }



    //核心方法
    public Integer getprivate(int e, int n) {
        easyRSA easyrsa = new easyRSA();
        //首先需要根据n求出两个素数p和q
        HashMap<Integer, Integer> map = brokenE(n);
        //然后根据p和q算出fineN
        if(map!=null){
            int p, q,fineN;
            Set<Integer> set = map.keySet();
            Object[] array = set.toArray();
            p=(Integer) array[0];
            q=(Integer) array[1];
            fineN=(p-1)*(q-1);
            //根据d满足d*e mod(fineN)==1遍历得到d
            System.out.println(" 大数分解得:p:"+p+"  q:"+q);
            Integer prid = easyrsa.getPrid(e, fineN);
            return prid;
        }else {
            System.out.println("请重新输入e和n");
            main(null);
            return null;
        }

    }

    //分解大数成p和q,基本讲究的就是一个穷举法,先把所有的有可能的p和q存起来
    public HashMap brokenE(int E) {
        easyRSA easyrsa = new easyRSA();
        HashMap<Integer, Integer> map = new HashMap<>();
        //穷举法算不了太大的E
        if (E < 10000000) {
            //10000000开平方不超过一万,若其有pq=e,则一定有一个p小于其开平方,不可能两个都大于其平方,这就是原理
            for (int i = 2; i < 10000; i++) {
                if (i > E) {
                    break;
                }
                for (int j = 2; j < 10000; j++) {
                    //提前退出循环,适用于E很小的情况
                    if (j > E) {
                        break;
                    }
                    if (E == i * j) {
                        //判断是否为素数
                        if (easyrsa.isPrime(i) && easyrsa.isPrime(j)) {
                            map.put(i, j);
                        }

                    }
                }

            }
        } else {
            System.out.println("此E太大,无法计算");
            return null;
        }
        if (!map.isEmpty()) {
//            System.out.println(map);
            return map;
        }else {
            System.out.println("找不到对应的p和q");
            return null;
        }

    }

    public static void main(String[] args) {
        easyRSA easyrsa = new easyRSA();
        FinePrivateCode finePrivateCode = new FinePrivateCode();
        System.out.println("---简单的已知公钥e和素数乘积n求用户私有密钥的程序---");

        Integer e = finePrivateCode.getE();
        Integer n = finePrivateCode.getN();
        Integer d = finePrivateCode.getprivate(e, n);
        System.out.println("解密得用户私钥为:"+d);

    }

}
