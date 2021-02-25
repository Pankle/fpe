package com.anji.src.fpe.config;

public interface Domain {
    Alphabet alphabet();
    int[] transform(String data);
    int[] transform(Integer data);
    int[] transformLong(String data);
    int[] transformAsc(String data);

    String transform(int[] data);
    String intTransform(int[] data);
    String longTransform(int[] data);

}
