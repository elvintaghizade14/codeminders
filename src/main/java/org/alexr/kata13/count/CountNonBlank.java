package org.alexr.kata13.count;

public final class CountNonBlank implements Count {
  @Override
  public int count(String s) {
    return s.isBlank() ? 0 : 1;
  }
}
