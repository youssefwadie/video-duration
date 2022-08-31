package com.github.youssefwadie.videoduration.core;



import java.util.Objects;

public record FileScanner(WeightCalculator weightCalculator, WeightPrinter weightPrinter) {
    public FileScanner {
        Objects.requireNonNull(weightCalculator);
        Objects.requireNonNull(weightPrinter);
    }
}
