package com.anji.src.fpe.builder.steps;

import com.anji.src.fpe.functions.prf.PseudoRandomFunction;

public interface WithPseudoRandomFunction {
    WithLengthRange withPseudoRandomFunction(PseudoRandomFunction pseudoRandomFunction);

    WithLengthRange withDefaultPseudoRandomFunction(byte[] key);
}
