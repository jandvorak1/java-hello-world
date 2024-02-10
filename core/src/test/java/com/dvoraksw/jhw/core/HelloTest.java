package com.dvoraksw.jhw.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HelloTest {

  @Test
  void greeting() {
    var hello = new Hello();
    assertEquals("Hello World!", hello.greeting());
  }
}
