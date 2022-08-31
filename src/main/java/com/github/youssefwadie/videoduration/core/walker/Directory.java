package com.github.youssefwadie.videoduration.core.walker;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

class Directory {
    private final Path path;
    private final List<Directory> children;

    private long totalWeight;

    Directory(Path path) {
        this(path, 0);
    }

    Directory(Path path, long totalWeight) {
        this.path = path;
        this.children = new LinkedList<>();
        this.totalWeight = totalWeight;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || (getClass() != o.getClass())) {
            return false;
        }
        Directory directory = (Directory) o;

        return Objects.equals(path, directory.path);
    }

    void addChild(Directory child) {
        this.children.add(child);
        totalWeight += child.getTotalWeight();
    }

    Path getPath() {
        return path;
    }

    List<Directory> getChildren() {
        return children;
    }

    long getTotalWeight() {
        return totalWeight;
    }

    void addToWeight(long weight) {
        this.totalWeight += weight;
    }

    void setWeight(long totalWeight) {
        this.totalWeight = totalWeight;
    }

}
