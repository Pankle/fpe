package com.anji.src.fpe.algorithm;

import com.anji.src.fpe.functions.prf.PseudoRandomFunction;

import java.util.ArrayList;

public interface Cipher {
    int[] encrypt(int[] plainText, Integer radix, byte[] tweak, PseudoRandomFunction pseudoRandomFunction);
    int[] decrypt(int[] cipherText, Integer radix, byte[] tweak, PseudoRandomFunction pseudoRandomFunction);
    ArrayList<int[]>  encrypt(ArrayList<Integer> plainText, Integer radix, byte[] tweak, PseudoRandomFunction pseudoRandomFunction);
}
