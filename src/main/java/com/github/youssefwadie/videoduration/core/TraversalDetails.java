package com.github.youssefwadie.videoduration.core;

import java.nio.file.Path;

public record TraversalDetails(String[] filesAssociations, boolean verbose, Path startingPath, int depth) {
    public TraversalDetails {
        startingPath = startingPath.toAbsolutePath();
    }
}
