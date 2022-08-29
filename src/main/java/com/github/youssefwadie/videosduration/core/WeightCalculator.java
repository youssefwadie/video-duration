package com.github.youssefwadie.videosduration.core;

import java.nio.file.Path;

@FunctionalInterface
public interface WeightCalculator {
    long calculate(Path file) throws Exception;
}
