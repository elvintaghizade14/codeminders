package org.alexr.kata13.val;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public abstract class Node {
  public final File file;
  public final int level;
  public final long count;
  public final List<Node> children;

  private Node(File file, int level, long count, List<Node> children) {
    this.file = file;
    this.level = level;
    this.count = count;
    this.children = children;
  }

  public static class NOther extends Node {
    public NOther(File file, int level) {
      super(file, level, 0, Collections.emptyList());
    }
  }

  public static class NFolder extends Node {
    public NFolder(File file, int level, List<Node> children) {
      super(
          file,
          level,
          children.stream()
            .map(n -> n.count)
            .reduce(Long::sum)
            .orElse(0L),
          children);
    }
  }

  public static class NFile extends Node {
    public NFile(File file, int level, Function<File, Long> counter) {
      super(file, level, counter.apply(file), Collections.emptyList());
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (getClass() != o.getClass()) return false;

    Node that = (Node) o;
    return level == that.level
        && count == that.count
        && Objects.equals(file, that.file)
        && Objects.equals(children, that.children);
  }

}

