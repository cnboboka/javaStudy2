package com.dlx.chapter06.ProcetedTest;

public class ObjectClass extends superClass{

    public static void main(String[] args) {
        ObjectClass o = new ObjectClass();
        o.x=3;
        System.out.println(o.x);
        superClass s=new superClass();
        System.out.println(s.x);
        System.out.println(s.getNum());
    }
}