package com.github.youssefwadie.durationcalculator.core;

import java.nio.file.Path;

@FunctionalInterface
public interface WeightCalculator {
    long calculate(Path file) throws Exception;
}
