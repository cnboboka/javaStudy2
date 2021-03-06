package com.dlx.chapter07.ExceptionTest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * A program that displays a trace feature of a recursive method call.
 *
 * @author Cay Horstmann
 * @version 1.01 2004-05-10
 */
public class StackTraceTest {
    /**
     * Computes the factorial of a number
     *
     * @param n a non-negative integer
     * @return n! = 1 * 2 * . . . * n
     */
    public static int factorial(int n) {
        System.out.println("factorial(" + n + "):");
        Throwable t = new Throwable();
        StackTraceElement[] frames = t.getStackTrace();
        for (StackTraceElement f : frames) {
            System.out.println(f);
        }
        int r;
        if (n <= 1) r = 1;
        else r = n * factorial(n - 1);
        System.out.println("return " + r);
        return r;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter n: ");
        int n = in.nextInt();
        factorial(n);
        /**
         * 断言表达式 assert 条件:表达式;
         * 系统会把表达式通过构造函数保存在AssertionError对象中
         * AssertionError对象不保存表达式，只是用表达式来创建一条字符串输出。
         * 开启断言需要设置运行参数   java -ea XXXX 。
         * 断言是有类加载器控制的
         */

        Throwable st = new Throwable();
       /* StringWriter stringWriter = new StringWriter();
        st.printStackTrace(new PrintWriter(stringWriter));
        String describtion = stringWriter.toString();*/
        System.out.println(Arrays.toString(st.getStackTrace()));
        assert 3 < 1 : new Exception("3不比1小");
    }
}
