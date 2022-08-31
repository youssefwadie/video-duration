package com.github.youssefwadie.videoduration.core;

import java.nio.file.Path;

@FunctionalInterface
public interface WeightCalculator {
    long calculate(Path file) throws Exception;
}
