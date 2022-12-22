package org.example.utils;

import java.text.DecimalFormat;

public class countUtils {
    /**
     * 计算每个字母出现的次数
     *
     * @param line
     */
    public String get(String line) {
        char[] str = line.toCharArray();
        int[] a = new int[26];//待会记录字母表中的哪个和个数,这就是统计特性
        double[] count=new double[26];//浮点数记录频率
        char[] t = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        int tablelength = t.length;
        int linelength = line.length();
        for (int i = 0; i < linelength; i++) {
            //循环对比字母表长度次
            for (int j = 0; j < tablelength; j++) {
                if (str[i] == t[j])
                    a[j]++;
            }
        }

        StringBuilder result = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        for (int j = 0; j < tablelength; j++) {
            if (a[j]!= 0) {
//                System.out.println(t[j] + "=" + a[j] + "  ");
                count[j]=((a[j]*1.0)/linelength)*100;
                String format = decimalFormat.format(count[j]);
                result.append(t[j]).append(":").append(format).append("% ");
            }
        }
//        System.out.println(result.toString());
        return result.toString();
    }
}
