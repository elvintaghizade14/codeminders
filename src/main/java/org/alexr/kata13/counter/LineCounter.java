package org.alexr.kata13.counter;

import lombok.SneakyThrows;
import org.alexr.kata13.count.Count;
import org.alexr.kata13.strip.Strip;
import org.alexr.kata13.strip.state.LineState;

import java.io.File;
import java.nio.file.Files;
import java.util.stream.Stream;

import static org.alexr.kata13.util.Fold.fold;

public class LineCounter {
  private final Strip stripper;
  private final Count counter;

  public LineCounter(Strip stripper, Count counter) {
    this.stripper = stripper;
    this.counter = counter;
  }

  public FileState fold_file(FileState acc, String line) {
    LineState ls = LineState.fresh(line, acc.inBlock);
    while (!ls.isDone()) ls = stripper.process(ls);
    return acc.updated(counter.count(ls.result()), ls.inBlock);
  }

  public long count(Stream<String> stream) {
    FileState zero = FileState.fresh();
    return fold(stream, zero, this::fold_file).count;
  }

  @SneakyThrows
  public long count(File file) {
    try (Stream<String> stream = Files.lines(file.toPath())) {
      return count(stream);
    }
  }
}
