package org.example;


import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import static java.lang.Math.pow;
import static java.lang.Math.scalb;

public class AppTest{
/*
    @Test
    public void TestisPrime(){
        boolean prime = new DH().isPrime(123);
        System.out.println(prime);
        boolean prime2 = new DH().isPrime(121);
        System.out.println(prime2);
        boolean prime3 = new DH().isPrime(12);
        System.out.println(prime3);
        boolean prime4 = new DH().isPrime(16);
        System.out.println(prime4);
        boolean prime5 = new DH().isPrime(97);
        System.out.println(prime5);
    }
    @Test
    public void TestGcd(){
        int gcd = new DH().GCD(16,4);
        System.out.println(gcd);
    }
    @Test
    public void TestMod(){
        BigInteger mod = new DH().ExpMod(BigInteger.valueOf(4),BigInteger.valueOf(2),BigInteger.valueOf(7));
        System.out.println(mod);
    }
*/

    @Test
    public void TestisprimerRoot(){
        //正确的
        BigInteger a=BigInteger.valueOf(5);
        BigInteger b=BigInteger.valueOf(96);
        BigInteger c=BigInteger.valueOf(97);

        BigInteger pow = a.pow(b.intValue());
        BigInteger result=pow.mod(c);
        System.out.println(result);
        System.out.println(result.intValue()==1);

        //错误的
        System.out.println(Math.pow(5.0,96.0));
        System.out.println(Math.pow(5.0,96.0)%97.0);

        System.out.println(Math.floorMod((long)Math.pow(5.0,96.0),(long) 97.0));
        System.out.println(Math.floorMod((long)Math.pow(5.0,96.0),(long) 97.0)==65);


//        temp.pow(flag).mod(BigInteger.valueOf(p)).equals(1)
    }

}