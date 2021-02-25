package com.anji.src.fpe;

import java.util.function.UnaryOperator;
import java.io.*;

@FunctionalInterface
interface DefaultMethod {
    void test();
    default int add (int a, int b){
        return a + b;
    }
}
public class TestBinary {
    UnaryOperator<Integer> unaryOperator1 = x -> {
        System.out.println(x);
        return x+1;
    };
    public static void main(String[] args) {
       /* String s = Integer.toBinaryString(6);
        System.out.println(s);*/
        File[] files = new File(".").listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isHidden();
            }
        });
        File[] listFiles = new File(".").listFiles(File::isHidden);
    }

}
