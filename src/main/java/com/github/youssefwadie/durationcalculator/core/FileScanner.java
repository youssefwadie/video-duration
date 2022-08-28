package com.github.youssefwadie.durationcalculator.core;



import java.util.Objects;

public record FileScanner(WeightCalculator weightCalculator, WeightPrinter weightPrinter) {
    public FileScanner {
        Objects.requireNonNull(weightCalculator);
        Objects.requireNonNull(weightPrinter);
    }
}
