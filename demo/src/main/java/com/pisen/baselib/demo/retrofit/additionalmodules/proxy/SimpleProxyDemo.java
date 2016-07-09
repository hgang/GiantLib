package com.pisen.baselib.demo.retrofit.additionalmodules.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by yangbo on 2016/4/7.
 * 简单的 动态代理例子
 *
 * @version 1.0
 */
public class SimpleProxyDemo {

    public interface HelloWorld {
        public void print();
        public void print2();
    }

    public static class Executor implements HelloWorld {
        @Override
        public void print() {
            System.out.println("HelloWorld");
        }

        @Override
        public void print2() {
            System.out.println("HelloWorlddddddd");
        }

    }

    public static void main(String args[]) {
        Executor executor = new Executor();
        Object obj = Proxy.newProxyInstance(executor.getClass().getClassLoader(), executor.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args1) throws Throwable {
                        System.out.println("im first");
                        Object obj1 = method.invoke(executor, args1);
                        System.out.println("im last");
                        return obj1;
                    }
                });
        ((HelloWorld) obj).print();
        ((HelloWorld) obj).print2();
    }
}
