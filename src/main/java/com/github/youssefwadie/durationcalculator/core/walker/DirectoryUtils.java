package com.github.youssefwadie.durationcalculator.core.walker;


import com.github.youssefwadie.durationcalculator.core.WeightPrinter;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

public class DirectoryUtils {

    private final static String[] COLORS = new String[]{
            AnsiColors.BLUE,
            AnsiColors.GREEN,
            AnsiColors.CYAN,
            AnsiColors.PURPLE,
    };

    public static void addChild(Directory start, Path parent, Directory child) {
        if (start.getPath().equals(parent)) {
            start.addChild(child);
            return;
        }

        List<Directory> children = start.getChildren();
        for (Directory rootChild : children) {
            if (rootChild.getPath().equals(parent)) {
                rootChild.addChild(child);
                return;
            }
            addChild(rootChild, parent, child);
        }
    }

    public static void addChildDuration(Directory start, Path parent, Directory child) {
        List<Directory> children = start.getChildren();
        for (Directory rootChild : children) {
            if (rootChild.getPath().equals(parent)) {
                rootChild.addToWeight(child.getTotalWeight());
                return;
            }
            addChildDuration(rootChild, parent, child);
        }
    }

    public static String print(Directory parent, int level, WeightPrinter weightPrinter, boolean lastChild) {
        StringBuilder out = new StringBuilder();
        if (level != 0) {
            for (int i = 0; i < level; i++) {
                out.append(COLORS[i % COLORS.length]).append("│   ");
            }
            out.append(COLORS[level % COLORS.length]);
            if (lastChild) {
                out.append("└── ");
            } else {
                out.append("├── ");
            }

            out.append(AnsiColors.RESET);
        }

        out.append(parent.getPath().getFileName()).append(": ")
                .append(weightPrinter.print(parent.getTotalWeight()))
                .append('\n');

        parent.getChildren().sort(Comparator.comparing(Directory::getPath));
        List<Directory> children = parent.getChildren();
        int childIndex = 0;
        for (Directory child : children) {
            out.append(DirectoryUtils
                    .print(child, level + 1, weightPrinter, childIndex == (children.size() - 1)));

            childIndex++;
        }

        return out.toString();
    }

    public static void removeChild(Directory start, Path child) {
        ListIterator<Directory> iterator = start.getChildren().listIterator();
        while (iterator.hasNext()) {
            Directory next = iterator.next();
            if (next.getPath().equals(child)) {
                iterator.remove();
                return;
            }
            removeChild(next, child);
        }
    }

    public static Directory getDirectory(Directory start, Path path) {
        if (start.getPath().equals(path)) {
            return start;
        }

        for (Directory child : start.getChildren()) {
            Directory target = getDirectory(child, path);
            if (target != null) {
                return target;
            }
        }

        return null;
    }

    public static void clean(Directory root) {
        ListIterator<Directory> iterator = root.getChildren().listIterator();
        while (iterator.hasNext()) {
            Directory child = iterator.next();
            if (child.getTotalWeight() == 0) {
                iterator.remove();
            } else {
                clean(child);
            }
        }
    }
}
