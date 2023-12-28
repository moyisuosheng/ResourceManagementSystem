package com.myss.file.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 文件哈希工具类
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/15
 */
public class FileHashUtil {
    public static String getMD5Hash(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        byte[] digest = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}