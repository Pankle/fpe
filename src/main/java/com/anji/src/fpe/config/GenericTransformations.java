package com.anji.src.fpe.config;

import com.anji.src.fpe.transformer.*;

import java.util.HashMap;
import java.util.Map;


public class GenericTransformations implements IntToTextTransformer, TextToIntTransformer, AscToIntTransformer, IntToIntTransformer, IntArraryToIntTransformer,
        LongToIntTransformer,IntArraryToLongTransformer {

    private final Map<Integer, Character> intToChar;
    private final Map<Character, Integer> charToInt;

    public GenericTransformations(char[] characters){
        intToChar = new HashMap<Integer, Character>(characters.length);
        charToInt = new HashMap<Character, Integer>(characters.length);
        for (int i = 0; i < characters.length; i++) {
            intToChar.put(i, characters[i]);
            charToInt.put(characters[i], i);
        }
    }

    @Override
    public String transform(int[] data) {
        StringBuilder builder = new StringBuilder(data.length);
        for (int number : data) {
            builder.append(intToChar.get(number));
        }
        return builder.toString();
    }

    @Override
    public int[] transform(String text) {
        char[] chars = text.toCharArray();
        int[] result = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            result[i] = charToInt.get(chars[i]);
        }
        return result;
    }

    @Override
    public int[] transformAsc(String text) {
        char[] chars = text.toCharArray();
        int[] result = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            result[i] = charToInt.get(chars[i]);
        }
        return result;
    }
/*
    @Override
    public int[] oneTransFormAsc(String data) {
        return new int[0];
    }

    @Override
    public int[] twoTransFormAsc(String data) {
        return new int[0];
    }

    @Override
    public int[] threeTransFormAsc(String data) {
        return new int[0];
    }*/

    @Override
    public int[] transform(Integer number) {
        char[] chars = number.toString().toCharArray();
        int[] result = new int[chars.length];
        for (int i = 0 ; i < chars.length;i++){
            result[i] = charToInt.get(chars[i]);
        }
        return result;
    }

    @Override
    public String inttransform(int[] data) {
        StringBuilder builder = new StringBuilder(data.length);
        for (int number : data) {
            builder.append(intToChar.get(number));
        }
        return builder.toString();
    }


    @Override
    public String inttolongtransform(int[] data) {
        StringBuilder builder = new StringBuilder(data.length);
        for (int number : data) {
            builder.append(intToChar.get(number));
        }
        return builder.toString();
    }

    @Override
    public int[] transformLong(String number) {
        char[] chars = number.toString().toCharArray();
        int[] result = new int[chars.length];
        for (int i = 0 ; i < chars.length;i++){
            result[i] = charToInt.get(chars[i]);
        }
        return result;
    }
}
