package org.example;

import org.junit.Test;

import java.util.HashMap;
import java.util.Set;

public class AppTest {
    @Test
    public void Testbroken() {
        FinePrivateCode finePrivateCode = new FinePrivateCode();
            HashMap map1 = finePrivateCode.brokenE(3599);
            HashMap map2 = finePrivateCode.brokenE(323);
            HashMap map3 = finePrivateCode.brokenE(889);


        System.out.println("----------------");
        System.out.println(map1);
        System.out.println(map2);
        System.out.println(map3);
        Set set = map3.keySet();
        System.out.println(set);

        //测试所有1000以内的素数
        for(int i = 3;i< 1000;i++)
            {
                HashMap map = finePrivateCode.brokenE(i);
                if (map!=null) {
                    System.out.println(i + ":" + map);
                    System.out.println("---");
                }
            }
        }
    }