package com.dlx.chapter06.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AdminServiceInvocation implements InvocationHandler {
    private Object target;

    public AdminServiceInvocation(Object target) {
        this.target = target;
        System.out.println("target : "+ target);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("****************"+method.getName()+"****************");
        System.out.println("proxy : "+ proxy.getClass());
        System.out.println("判断用户是否有权进行操作");
        Object obj = method.invoke(target);
        System.out.println("记录用户执行操作的用户信息，变更内容和时间等");
        return obj;
    }
}
