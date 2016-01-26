package br.com.estudio89.grabber.test;

import java.util.ArrayList;
import java.util.List;

public class TestInterfaceFactory {
  public static List<br.com.estudio89.grabber.test.TestInterface> listAll() {
    List<br.com.estudio89.grabber.test.TestInterface> items = new ArrayList<>();
    items.add(new TestClass());
    return items;
  }
}