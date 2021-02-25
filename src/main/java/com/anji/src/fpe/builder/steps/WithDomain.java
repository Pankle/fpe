package com.anji.src.fpe.builder.steps;

import com.anji.src.fpe.config.Domain;

public interface WithDomain {
    WithPseudoRandomFunction withDomain(Domain basicAlphabetDomain);

    WithPseudoRandomFunction withDefaultDomain();
}
