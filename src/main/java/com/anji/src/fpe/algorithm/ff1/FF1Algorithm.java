package com.anji.src.fpe.algorithm.ff1;

import com.anji.src.fpe.functions.prf.PseudoRandomFunction;
import com.anji.src.fpe.data.ByteString;
import com.anji.src.fpe.data.IntString;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import static com.anji.src.fpe.functions.ComponentFunctions.num;
import static com.anji.src.fpe.functions.ComponentFunctions.stringOf;
import static com.anji.src.fpe.functions.DataFunctions.*;
import static java.lang.Math.ceil;

class FF1Algorithm {

    private static final int NUMBER_OF_ROUNDS = 10;

    private FF1Algorithm (){}

    static int[] encrypt(int[] plainText, Integer radix, byte[] tweak, PseudoRandomFunction pseudoRandomFunction) {
        IntString target = new IntString(plainText);
        int leftSideLength = target.leftSideLength();
        int rightSideLength = target.rightSideLength();
        int lengthOfLeftAfterEncoded = (int) ceil(ceil(rightSideLength * log(radix)) / 8.0);
        int paddingToEnsureFeistelOutputIsBigger = (int) (4 * ceil(lengthOfLeftAfterEncoded / 4.0) + 4);
        ByteString padding = generateInitialPadding(radix, target.length(), tweak.length, leftSideLength);

        int[] left = target.left();
        int[] right = target.right();
        for (int round=0; round<NUMBER_OF_ROUNDS; round++) {
            BigInteger roundNumeral = roundNumeral(num(right, radix), tweak, padding, pseudoRandomFunction, lengthOfLeftAfterEncoded, paddingToEnsureFeistelOutputIsBigger, round);
            int partialLength = round % 2 == 0 ? leftSideLength : rightSideLength;
            BigInteger partialNumeral = num(left, radix).add(roundNumeral).mod(BigInteger.valueOf(radix).pow(partialLength));
            int[] partialBlock = stringOf(partialLength, radix, partialNumeral);
            left = right;
            right = partialBlock;
        }
        return concatenate(left, right);
    }

    //asc码加密
    static ArrayList<int[]> encrypt(ArrayList<Integer> plainText, Integer radix, byte[] tweak, PseudoRandomFunction pseudoRandomFunction) {
        ArrayList<int[]> encrypList= new ArrayList();
        for (Integer plain:plainText){
            int plains[] =intTransIntColletion(plain);
            IntString target = new IntString(plains);
            int leftSideLength = target.leftSideLength();
            int rightSideLength = target.rightSideLength();
            int lengthOfLeftAfterEncoded = (int) ceil(ceil(rightSideLength * log(radix)) / 8.0);
            int paddingToEnsureFeistelOutputIsBigger = (int) (4 * ceil(lengthOfLeftAfterEncoded / 4.0) + 4);
            ByteString padding = generateInitialPadding(radix, target.length(), tweak.length, leftSideLength);

            int[] left = target.left();
            int[] right = target.right();
            for (int round=0; round<NUMBER_OF_ROUNDS; round++) {
                BigInteger roundNumeral = roundNumeral(num(right, radix), tweak, padding, pseudoRandomFunction, lengthOfLeftAfterEncoded, paddingToEnsureFeistelOutputIsBigger, round);
                int partialLength = round % 2 == 0 ? leftSideLength : rightSideLength;
                BigInteger partialNumeral = num(left, radix).add(roundNumeral).mod(BigInteger.valueOf(radix).pow(partialLength));
                int[] partialBlock = stringOf(partialLength, radix, partialNumeral);
                left = right;
                right = partialBlock;
            }

            encrypList.add(concatenate(left, right));
        }
     return encrypList;
    }

    //将int数组类型转换成int数据类型
    private static int intColletionTransInt(int[] concatenate) {
        // 自定义一个字符缓冲区，
        StringBuilder sb = new StringBuilder();
        //遍历int数组，并将int数组中的元素转换成字符串储存到字符缓冲区中去
        for(int i=0;i<concatenate.length;i++)
        {
            if(i!=concatenate.length-1)
                sb.append(concatenate[i]);
        }
        return Integer.valueOf(sb.toString());
    }

    //将int类型数据转换成int数组
    private static int[] intTransIntColletion(Integer plain) {
        String string = plain.toString();
        int a[] = new int[string.length()];
        for (int i = 0; i < string.length()-1; i++) {
            a[i]=string.indexOf(i);
        }

        return  a;
    }

    static int[] decrypt(int[] cipherText, Integer radix, byte[] tweak, PseudoRandomFunction pseudoRandomFunction) {
        IntString target = new IntString(cipherText);
        int leftSideLength = target.leftSideLength();
        int rightSideLength = target.rightSideLength();
        int lengthOfLeftAfterEncoded = (int) ceil(ceil(rightSideLength * log(radix)) / 8.0);
        int paddingToEnsureFeistelOutputIsBigger = (int) (4 * ceil(lengthOfLeftAfterEncoded / 4.0) + 4);
        ByteString padding = generateInitialPadding(radix, target.length(), tweak.length, leftSideLength);

        int[] left = target.left();
        int[] right = target.right();
        for (int round=NUMBER_OF_ROUNDS-1; round>=0; round--) {
            BigInteger roundNumeral = roundNumeral(num(left, radix), tweak, padding, pseudoRandomFunction, lengthOfLeftAfterEncoded, paddingToEnsureFeistelOutputIsBigger, round);
            int partialLength = round % 2 == 0 ? leftSideLength : rightSideLength;
            BigInteger partialNumeral = num(right, radix).subtract(roundNumeral).mod(BigInteger.valueOf(radix).pow(partialLength));
            int[] partialBlock = stringOf(partialLength, radix, partialNumeral);
            right = left;
            left = partialBlock;
        }
        return concatenate(left, right);
    }

    private static BigInteger roundNumeral(BigInteger targetBlockNumeral, byte[] tweak, ByteString padding, PseudoRandomFunction pseudoRandomFunction, int lengthOfLeftAfterEncoded, int paddingToEnsureFeistelOutputIsBigger, int round) {
        ByteString q = generateQ(new ByteString(tweak), targetBlockNumeral, lengthOfLeftAfterEncoded, round);
        ByteString roundBlock = roundFunction(pseudoRandomFunction, paddingToEnsureFeistelOutputIsBigger, padding, q);
        return num(Arrays.copyOf(roundBlock.raw(), paddingToEnsureFeistelOutputIsBigger));
    }

    private static ByteString roundFunction(PseudoRandomFunction pseudoRandomFunction, int paddingToEnsureFeistelOutputIsBigger, ByteString padding, ByteString q) {
        byte[] r = pseudoRandomFunction.apply(padding.concatenate(q).raw());
        ByteString s = new ByteString(r);
        for (int j = 1; j <= ceil(paddingToEnsureFeistelOutputIsBigger / 16.0) - 1; j++) {
            s = s.concatenate(new ByteString(pseudoRandomFunction.apply(xor(r, numberAsArrayOfBytes(j, 16).getData()))));
        }
        return s;
    }

    private static ByteString generateQ(ByteString tweak, BigInteger targetSideNumeral, int lengthOfLeftAfterEncoded, int round) {
        return tweak
                .concatenate(numberAsArrayOfBytes(0, mod(- tweak.length() - lengthOfLeftAfterEncoded - 1, 16)))
                .concatenate(numberAsArrayOfBytes(round, 1))
                .concatenate(numberAsArrayOfBytes(targetSideNumeral, lengthOfLeftAfterEncoded));
    }

    private static ByteString generateInitialPadding(Integer radix, int textLength, int tweakLength, int leftSideLength) {
        return new ByteString(new byte[]{ (byte) 0x01, (byte) 0x02, (byte) 0x01 } )
                .concatenate(numberAsArrayOfBytes(radix, 3))
                .concatenate(new ByteString( new byte[] { (byte) 0x0A } ))
                .concatenate(new ByteString( new byte[] { (byte) (mod(leftSideLength, 256) & 0xFF) }))
                .concatenate(numberAsArrayOfBytes(textLength, 4))
                .concatenate(numberAsArrayOfBytes(tweakLength, 4));
    }
}
