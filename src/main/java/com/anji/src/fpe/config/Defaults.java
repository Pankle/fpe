package com.anji.src.fpe.config;

import com.anji.src.fpe.config.basic.BasicAlphabet;
import com.anji.src.fpe.transformer.*;


public class Defaults {

    private Defaults(){}

    public static final BasicAlphabet ALPHABET = new BasicAlphabet();
    private static final TextToIntTransformer TEXT_TO_INT_TRANSFORMER = new GenericTransformations(ALPHABET.availableCharacters());
    private static final IntToTextTransformer INT_TO_TEXT_TRANSFORMER = new GenericTransformations(ALPHABET.availableCharacters());
    private static final AscToIntTransformer ASC_TO_INT_TRANSFORMER = new GenericTransformations(ALPHABET.chinaCharacter());


    private static final IntToIntTransformer INT_TO_INT_TRANSFORMER = new GenericTransformations(ALPHABET.availableCharacter());
    private static final IntArraryToIntTransformer INT_ARRARY_TO_INT_TRANSFORMER = new GenericTransformations(ALPHABET.chinaCharacter());
    private static final LongToIntTransformer LONG_TO_INT_TRANSFORMER = new GenericTransformations(ALPHABET.availableCharacter());
    private static final IntArraryToLongTransformer INT_ARRARY_TO_LONG_TRANSFORMER = new GenericTransformations(ALPHABET.availableCharacter());

    public static final Domain DOMAIN = new GenericDomain(ALPHABET, TEXT_TO_INT_TRANSFORMER, INT_TO_TEXT_TRANSFORMER, ASC_TO_INT_TRANSFORMER
            ,INT_TO_INT_TRANSFORMER, INT_ARRARY_TO_INT_TRANSFORMER, LONG_TO_INT_TRANSFORMER, INT_ARRARY_TO_LONG_TRANSFORMER);
    //定义加密最长最短内容范围
    public static final Integer DEFAULT_MAX_LENGTH = Integer.MAX_VALUE;
    public static final Integer DEFAULT_MIN_LENGTH = 2;
    public static final LengthRange LENGTH_RANGE = new LengthRange(DEFAULT_MIN_LENGTH, DEFAULT_MAX_LENGTH);
}
