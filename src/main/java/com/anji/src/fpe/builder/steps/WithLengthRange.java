package com.anji.src.fpe.builder.steps;

import com.anji.src.fpe.config.LengthRange;

public interface WithLengthRange {
    Builder withLengthRange(LengthRange lengthRange);

    Builder withDefaultLengthRange();
}
