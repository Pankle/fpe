package com.anji.src.fpe.config;

import com.anji.src.fpe.transformer.*;


public class GenericDomain implements Domain{

    private final Alphabet alphabet;
    private final TextToIntTransformer textToIntTransformer;
    private final IntToTextTransformer intToTextTransformer;
    private final AscToIntTransformer ascToIntTransformer;
    private final IntToIntTransformer intToIntTransformer;
    private final IntArraryToIntTransformer inttransform;
    private final LongToIntTransformer longToIntTransformer;
    private final IntArraryToLongTransformer intArraryToLongTransformer;

    public GenericDomain(Alphabet alphabet, TextToIntTransformer textToIntTransformer, IntToTextTransformer intToTextTransformer
                        , AscToIntTransformer ascToIntTransformer, IntToIntTransformer intToIntTransformer, IntArraryToIntTransformer inttransform,
                         LongToIntTransformer longToIntTransformer, IntArraryToLongTransformer intArraryToLongTransformer) {
        this.alphabet = alphabet;
        this.textToIntTransformer = textToIntTransformer;
        this.intToTextTransformer = intToTextTransformer;
        this.ascToIntTransformer = ascToIntTransformer;
        this.intToIntTransformer = intToIntTransformer;
        this.inttransform = inttransform;
        this.longToIntTransformer = longToIntTransformer;
        this.intArraryToLongTransformer = intArraryToLongTransformer;
    }

    @Override
    public Alphabet alphabet() {
        return alphabet;
    }

    @Override
    public int[] transform(String data) {
        return textToIntTransformer.transform(data);
    }

    @Override
    public int[] transform(Integer data) {
        return intToIntTransformer.transform(data);
    }

    @Override
    public int[] transformLong(String data) {
        return longToIntTransformer.transformLong(data);
    }

    @Override
    public int[] transformAsc(String data) {
        return ascToIntTransformer.transformAsc(data);
    }

    @Override
    public String transform(int[] data) {
        return intToTextTransformer.transform(data);
    }

    @Override
    public String intTransform(int[] data) {
        return inttransform.inttransform(data);
    }

    @Override
    public String longTransform(int[] data) {
        return intArraryToLongTransformer.inttolongtransform(data);
    }

}
