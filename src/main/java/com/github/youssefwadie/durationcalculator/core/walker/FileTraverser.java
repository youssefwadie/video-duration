package com.github.youssefwadie.durationcalculator.core.walker;

import com.github.youssefwadie.durationcalculator.core.WeightCalculator;

import java.awt.*;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

class FileTraverser implements FileVisitor<Path> {
    private final String regex;
    private final Directory root;
    private long totalVisitedDuration;

    private Directory currentVisitedDirectory;

    private final WeightCalculator weightCalculator;

    private final boolean verbose;

    FileTraverser(Path startingPath, WeightCalculator weightCalculator, boolean verbose, String... filesAssociations) {
        this.regex = createRegex(filesAssociations);
        this.root = new Directory(startingPath);
        this.weightCalculator = weightCalculator;
        this.verbose = verbose;
        totalVisitedDuration = 0;
    }

    FileTraverser(Path startingPath,
                  WeightCalculator weightCalculator, boolean verbose) {
        this(startingPath, weightCalculator, verbose, "mp4", "mkv", "wmv");
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Objects.requireNonNull(dir);
        Objects.requireNonNull(attrs);

        this.currentVisitedDirectory = new Directory(dir);

        DirectoryUtils.addChild(root, dir.getParent(), this.currentVisitedDirectory);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Objects.requireNonNull(file);
        Objects.requireNonNull(attrs);

        if (attrs.isRegularFile() && file.getFileName().toString().matches(regex)) {
            long weight = 0;
            try {
                weight = weightCalculator.apply(file);
            } catch (Exception e) {
                if (verbose) {
                    System.err.printf("%s%s%s%n", AnsiColors.RED, e.getMessage(), AnsiColors.RESET);
                }
            }
            currentVisitedDirectory.addToWeight(weight);
            totalVisitedDuration += weight;
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        Objects.requireNonNull(dir);
        if (exc != null && verbose) {
            System.err.printf("%s%s%s%n", AnsiColors.RED, exc.getMessage(), AnsiColors.RESET);
        }

        if (dir.equals(root.getPath())) {
            root.setWeight(totalVisitedDuration);
        }

        if (currentVisitedDirectory.getTotalWeight() == 0) {
            DirectoryUtils.removeChild(root, dir);
        } else {
            DirectoryUtils.addChildDuration(root, dir.getParent(), currentVisitedDirectory);
        }

        Directory parentDirectory = DirectoryUtils.getDirectory(root, dir.getParent());
        if (parentDirectory != null) currentVisitedDirectory = parentDirectory;

        return FileVisitResult.CONTINUE;
    }

    private static String createRegex(String... suffix) {
        return String.format(".*\\.(%s)", String.join("|", suffix));
    }


    Directory getRoot() {
        return root;
    }
}
