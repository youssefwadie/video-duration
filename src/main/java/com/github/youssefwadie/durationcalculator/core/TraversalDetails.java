package com.github.youssefwadie.durationcalculator.core;

import java.nio.file.Path;

public record TraversalDetails(String[] filesAssociations, boolean verbose, Path startingPath, int depth) {

}
