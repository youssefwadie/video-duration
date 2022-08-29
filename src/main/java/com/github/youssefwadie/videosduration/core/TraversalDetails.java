package com.github.youssefwadie.videosduration.core;

import java.nio.file.Path;

public record TraversalDetails(String[] filesAssociations, boolean verbose, Path startingPath, int depth) {

}
