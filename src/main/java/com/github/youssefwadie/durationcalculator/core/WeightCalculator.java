package com.github.youssefwadie.durationcalculator.core;

import java.nio.file.Path;

@FunctionalInterface
public interface WeightCalculator {
    long apply(Path file) throws Exception;
}
