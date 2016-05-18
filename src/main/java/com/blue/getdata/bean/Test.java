package com.blue.getdata.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Created by saxer on 1/21/16.
 */
public class Test {

    public interface StyleInterface {
        public String word();
    }

    public enum StyleP {

    }

    public enum Style implements StyleInterface {

        NORMAL("normal_xxx") {
            @Override
            public String word() {
                return this.word;
            }
        },
        PROGRESS("progressd") {
            @Override
            public String word() {
                return this.word;
            }
        };
        String word;

        Style(String word) {
            this.word = word;
        }
    }

    public static void main(String[] args) throws Exception {
        Test test = new Test();
//        test.enumTest();
        test.listenerTest();
    }

    private void listenerTest() throws Exception {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        Class<?> classStyle = loader.loadClass("com.blue.getdata.bean.Test$StyleInterface");

        Constructor[] constructors = classStyle.getConstructors();
        for (Constructor c : constructors) {
            c.setAccessible(true);
            System.out.println(c.getParameterTypes().length);
        }
        Object instance = classStyle.newInstance();
        System.out.println(instance);
    }

    private void enumTest() throws Exception {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        Class<?> classStyle = loader.loadClass("com.blue.getdata.bean.Test$Style");
        Field[] fields = classStyle.getFields();
        for (Field field : fields) {
            field.setAccessible(true);

            StyleInterface filedN = (StyleInterface) field.get(field.getType());
            System.out.println(field.getName() + "\tvalue:" + filedN.word());
        }
    }
}
