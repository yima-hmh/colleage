package org.example;

import java.io.*;
import java.security.*;

public class RSA {
        /**
         * 秘钥对算法名称
         */
        private static final String ALGORITHM = "RSA";

        /**
         * 密钥长度
         */
        private static final int KEY_SIZE = 2048;

        /**
         * 签名算法
         */
        private static final String SIGNATURE_ALGORITHM = "Sha1WithRSA";

        /**
         * 随机生成 RSA 密钥对（包含公钥和私钥）
         */
        public static KeyPair generateKeyPair() throws Exception {
            // 获取指定算法的密钥对生成器
            KeyPairGenerator gen = KeyPairGenerator.getInstance(ALGORITHM);

            // 初始化密钥对生成器（指定密钥长度, 使用默认的安全随机数源）
            gen.initialize(KEY_SIZE);

            // 随机生成一对密钥（包含公钥和私钥）
            return gen.generateKeyPair();
        }

        /**
         * 私钥签名（数据）: 用私钥对指定字节数组数据进行签名, 返回签名信息
         */
        public static byte[] sign(byte[] data, PrivateKey priKey) throws Exception {
            // 根据指定算法获取签名工具
            Signature sign = Signature.getInstance(SIGNATURE_ALGORITHM);

            // 用私钥初始化签名工具
            sign.initSign(priKey);

            // 添加要签名的数据
            sign.update(data);

            // 计算签名结果（签名信息）
            byte[] signInfo = sign.sign();

            return signInfo;
        }




        /**
         * 公钥验签（数据）: 用公钥校验指定数据的签名是否来自对应的私钥
         * 无论是文本,还是文件,在本工程中都假设其明文正常传输,然后计算出和原来相同的MD5值
         * 传入的是明文计算出来的MD5哈希值,以及私钥加密的MD5哈希值,公钥
         * 私钥加密的信息解密之后,若其MD5值与传进来的MD5值相同,不就证明没做修改嘛
         */
        public static boolean verify(byte[] data, byte[] signInfo, PublicKey pubKey) throws Exception {
            //
            // 根据指定算法获取签名工具
            Signature sign = Signature.getInstance(SIGNATURE_ALGORITHM);

            // 用公钥初始化签名工具
            sign.initVerify(pubKey);

            // 添加要校验的数据
            sign.update(data);

            // 校验数据的签名信息是否正确,
            // 如果返回 true, 说明该数据的签名信息来自该公钥对应的私钥,
            // 同一个私钥的签名, 数据和签名信息一一对应, 只要其中有一点修改, 则用公钥无法校验通过,
            // 因此可以用私钥签名, 然后用公钥来校验数据的完整性与签名者（所有者）
            boolean verify = sign.verify(signInfo);

            return verify;
        }

        private static void close(Closeable c) {
            if (c != null) {
                try {
                    c.close();
                } catch (IOException e) {
                    // nothing
                }
            }
        }

}

