package org.example;

import org.example.utils.countUtils;

import java.util.Scanner;

/**
 * 自定义密钥位数的恺撒密码加密和解密算法，明文用小写表示，密文用大写表示。
 * 输入1或者2选择所需执行的算法，1为加密算法，2为解密算法。
 * 若为加密操作，可以输入大小大于或等于0的密钥,不规定一定要小于26,大于26将求余来运算
 * 输入密文或明文执行所需的解密或加密操作。
 */
public class Caesar
{
    private int key;
    private Scanner scan;

    /**
     * 加密算法，输入小写表示的明文，输出大写表示的密文
     * 将输入的字符串转化为字符数组，并在转化的同时利用ASCII表对字符进行加法移位操作并逐个输出。
     * 在进行加法移位操作时，判断该字符是否为其他符号，若是，则无需进行移位。
     * 并判断加法操作后是否超出26个小写字母的范围，若超出，则退回到ASCII表中的前26个位置。
     */
    public void encrypt()
    {
        System.out.println("Please enter the key of Caesar Algorithm: ");
        scan = new Scanner(System.in);

        int temp = scan.nextInt();

        if(temp >=0 ) key =temp%26;
        else
        {
            System.out.println("Invalid key!");
            //重新回来开始方法
            new Caesar().encrypt();
        }

        System.out.println("Please enter the plaintext: ");
        Scanner scan = new Scanner(System.in);
        String pt = scan.nextLine();
        scan.close();

        //应该对字符串进行去空格和小写化的预处理
        pt=pt.replaceAll(" ","").toLowerCase();
        //展示字符串的统计信息
        show(pt);

        char[] array = pt.toCharArray();
        System.out.print("The ciphertext is: ");
        char[] c = new char[pt.length()];
        StringBuilder st=new StringBuilder();
        for(int i = 0; i < pt.length(); i++)
        {
            c[i] = pt.charAt(i);
            if(c[i] >= 97 && c[i] <= 122)
            {
                c[i] += key;
                if(c[i] > 122) c[i] -= 26;//循环
                c[i] -= 32;
            }
            System.out.print(c[i]);
            st.append(c[i]);
        }
        System.out.println();
        show(st.toString().replaceAll(" ","").toLowerCase());
    }

    /**
     * 解密算法:输入大写的密文,然后输出小写的明文
     * 将输入的字符串转化为字符数组，并在转化的同时利用ASCII表对字符进行减法移位操作并逐个输出。
     * 在进行减法移位操作时，判断该字符是否为其他符号，若是，则无需进行移位。
     * 并判断减法操作后是否小于26个大写字母的范围，若小于，则前进到ASCII表中的后26个位置。
     */
    public void decrypt()
    {
        System.out.println("Please enter the key of Caesar Algorithm: ");
        scan = new Scanner(System.in);

        int temp = scan.nextInt();

        if(temp >=0 ) key =temp%26;
        else
        {
            System.out.println("Invalid key!");
            //重新回来开始方法
            new Caesar().decrypt();
        }

        System.out.println("Please enter the ciphertext: ");
        Scanner scan = new Scanner(System.in);
        String pt = scan.nextLine();
        scan.close();

        //应该对字符串进行去空格和大写化的预处理
        pt=pt.replaceAll(" ","").toUpperCase();
        //记得药变成小写,那边的数据才好处理
        show(pt.toLowerCase());
        char[] array = pt.toCharArray();

        System.out.println("The plaintext is: ");
        char[] c = new char[pt.length()];
        StringBuilder st=new StringBuilder();
        for(int i = 0; i < pt.length(); i++)
        {
            c[i] = pt.charAt(i);
            //加密是前进,那么解密就是后退
            if(c[i] >= 41 && c[i] <= 90)
            {
                c[i] -= key;
                if(c[i] <42) c[i]+= 26;//循环
                c[i] += 32;
            }
            System.out.print(c[i]);
            st.append(c[i]);
        }
        System.out.println();
        show(st.toString().replaceAll(" ","").toLowerCase());
    }

    /**
     * 展示频率信息
     * @param stat
     */
    public void show(String stat){
        System.out.println("The frequency of occurrences of each letter of this string is calculated as follows:");
        String stat1 = new countUtils().get(stat);
        System.out.println(stat1);
    }

    public static void main(String[] args)
    {
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
        Caesar c = new Caesar();
        if(flag == 1) c.encrypt();
        else if(flag == 2) c.decrypt();

        s.close();
    }
}
