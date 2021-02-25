package com.anji.src.fpe.algorithm.ff1;

import com.anji.src.fpe.functions.prf.PseudoRandomFunction;

import java.util.ArrayList;

public class Cipher implements com.anji.src.fpe.algorithm.Cipher {


    @Override
    public int[] encrypt(int[] plainText, Integer radix, byte[] tweak, PseudoRandomFunction pseudoRandomFunction) {
        return FF1Algorithm.encrypt(plainText, radix, tweak, pseudoRandomFunction);
    }

    @Override
    public int[] decrypt(int[] cipherText, Integer radix, byte[] tweak, PseudoRandomFunction pseudoRandomFunction) {
        return FF1Algorithm.decrypt(cipherText, radix, tweak, pseudoRandomFunction);
    }

    @Override
    public ArrayList<int[]> encrypt(ArrayList<Integer> plainText, Integer radix, byte[] tweak, PseudoRandomFunction pseudoRandomFunction) {
        return  FF1Algorithm.encrypt(plainText, radix, tweak, pseudoRandomFunction);
    }
}
