package com.github.youssefwadie.durationcalculator.core.walker;

import com.github.youssefwadie.durationcalculator.core.TraversalDetails;
import com.github.youssefwadie.durationcalculator.core.FileScanner;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;

public class FileWalker {
    private final FileTraverser fileTraverser;
    private final FileScanner scanner;
    private final Path startingPath;
    private final int depth;
    private boolean visited;

    public FileWalker(TraversalDetails traversalDetails,
                      FileScanner scanner) {
        this.startingPath = traversalDetails.startingPath();
        this.depth = traversalDetails.depth();

        this.fileTraverser = new FileTraverser(startingPath,
                scanner.weightCalculator(),
                traversalDetails.verbose(),
                traversalDetails.filesAssociations());

        this.scanner = scanner;
        this.visited = false;
    }

    public String visit() throws IOException {
        if (!visited) {
            Files.walkFileTree(startingPath, EnumSet.noneOf(FileVisitOption.class), depth, fileTraverser);
//            DirectoryUtils.clean(fileTraverser.getRoot());
            visited = true;
        }
        return DirectoryUtils.print(fileTraverser.getRoot(), 0, scanner.weightPrinter(), false);
    }

}
