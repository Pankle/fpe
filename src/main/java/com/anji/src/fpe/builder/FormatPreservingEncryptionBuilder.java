package com.anji.src.fpe.builder;

import com.anji.src.fpe.FormatPreservingEncryption;
import com.anji.src.fpe.algorithm.ff1.Cipher;
import com.anji.src.fpe.builder.steps.Builder;
import com.anji.src.fpe.builder.steps.WithDomain;
import com.anji.src.fpe.builder.steps.WithLengthRange;
import com.anji.src.fpe.builder.steps.WithPseudoRandomFunction;
import com.anji.src.fpe.functions.prf.DefaultPseudoRandomFunction;
import com.anji.src.fpe.functions.prf.PseudoRandomFunction;
import com.anji.src.fpe.config.Defaults;
import com.anji.src.fpe.config.Domain;
import com.anji.src.fpe.config.LengthRange;

public class FormatPreservingEncryptionBuilder {

    private static final String AVOID_INSTANCE_MESSAGE = "use class throw static methods";
    private static final com.anji.src.fpe.algorithm.Cipher ff1 = new Cipher();

    private FormatPreservingEncryptionBuilder () {
        throw  new IllegalArgumentException(AVOID_INSTANCE_MESSAGE);
    }
    public static WithDomain ff1Implementation() {
        return new WithDomainStep(ff1);
    }

    private static class WithDomainStep implements WithDomain {

        private com.anji.src.fpe.algorithm.Cipher cipher;

        public WithDomainStep(com.anji.src.fpe.algorithm.Cipher cipher) {
            this.cipher = cipher;
        }

        @Override
        public WithPseudoRandomFunction withDomain(Domain domain) {
            return new WithPseudoRandomFunctionStep(cipher, domain);
        }

        @Override
        public WithPseudoRandomFunction withDefaultDomain() {
            return withDomain(Defaults.DOMAIN);
        }
    }

    private static class WithPseudoRandomFunctionStep implements WithPseudoRandomFunction {

        private com.anji.src.fpe.algorithm.Cipher cipher;
        private final Domain selectedDomain;

        private WithPseudoRandomFunctionStep(com.anji.src.fpe.algorithm.Cipher cipher, Domain selectedDomain) {
            this.cipher = cipher;
            this.selectedDomain = selectedDomain;
        }

        @Override
        public WithLengthRange withPseudoRandomFunction(PseudoRandomFunction pseudoRandomFunction) {
            return new WithLengthRangeStep(cipher, selectedDomain, pseudoRandomFunction);
        }

        @Override
        public WithLengthRange withDefaultPseudoRandomFunction(byte[] key) {
            return withPseudoRandomFunction(new DefaultPseudoRandomFunction(key));
        }
    }

    private static class WithLengthRangeStep implements WithLengthRange {
        private final com.anji.src.fpe.algorithm.Cipher cipher;
        private final Domain selectedDomain;
        private final PseudoRandomFunction selectedPRF;

        private WithLengthRangeStep(com.anji.src.fpe.algorithm.Cipher cipher, Domain selectedDomain, PseudoRandomFunction selectedPRF) {
            this.cipher = cipher;
            this.selectedDomain = selectedDomain;
            this.selectedPRF = selectedPRF;
        }


        @Override
        public Builder withLengthRange(LengthRange lengthRange) {
            return new TheBuilder(cipher, selectedDomain, selectedPRF, lengthRange);
        }

        @Override
        public Builder withDefaultLengthRange() {
            return withLengthRange(Defaults.LENGTH_RANGE);
        }
    }

    private static class TheBuilder implements Builder{
        private final com.anji.src.fpe.algorithm.Cipher cipher;
        private final Domain selectedDomain;
        private final PseudoRandomFunction selectedPRF;
        private final LengthRange lengthRange;

        private TheBuilder(com.anji.src.fpe.algorithm.Cipher cipher, Domain selectedDomain, PseudoRandomFunction selectedPRF, LengthRange lengthRange) {
            this.cipher = cipher;
            this.selectedDomain = selectedDomain;
            this.selectedPRF = selectedPRF;
            this.lengthRange = lengthRange;
        }

        @Override
        public FormatPreservingEncryption build() {
            return new FormatPreservingEncryption(cipher, selectedDomain, selectedPRF, lengthRange);
        }
    }
}
