package com.deng;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
public class Test {

    long width;

    public Test(long l) {
        width = l;
    }

    public static void main(String arg[]) {
        Test a, b, c;
        a = new Test(42L);
        b = new Test(42L);
        c = b;
        long s = 42L;


        System.out.println(a == b);
        //System.out.println(s == a);

        System.out.println(b == c);
        System.out.println(a.equals(s));
    }


}
