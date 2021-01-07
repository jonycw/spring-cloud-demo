package com.mint.shiro.test;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @Author: cw
 * @Date: 2021/1/7 15:49
 * @Description:
 */
public class Md5Test {

    /**
     * 散列算法一般用于生成一段文本的摘要信息，散列算法不可逆，也就是将内容生成摘要，但是反过来通过摘要生成内容是不可以的。
     * 散列算法常用于对密码进行散列，常用的散列算法有MD5、SHA。
     * 一般散列算法需要提供一个salt(盐)与原始内容生成摘要，这样做的目的是为了安全性。
     * @param args
     */
    public static void main(String[] args) {
        Md5Hash  md5Hash = new Md5Hash("123456");
        System.out.println("md5加密，不加盐："+md5Hash.toString());

        //md5加密，加盐，一次hash
        String password_md5_sale_1 = new Md5Hash("123456", "abc", 1).toString();
        System.out.println("md5加密，加盐，一次hash："+password_md5_sale_1);

        //md5加密，加盐，两次hash
        String password_md5_sale_2 = new Md5Hash("123456", "ceo", 2).toString();
        System.out.println("md5加密，加盐，两次hash："+password_md5_sale_2);//相当于md5(md5('1111'))

        //使用simpleHash
        String simpleHash = new SimpleHash("MD5", "123456", "ceo", 2).toString();
        System.out.println("simpleHash方式:"+simpleHash);
    }
}

