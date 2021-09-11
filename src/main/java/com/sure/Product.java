package com.sure;

import java.lang.reflect.*;
import java.text.*;
import java.util.Locale;

public class Product {
  private int id;
  private String name;
  private String ean;
  private double price;

  public Product() {
    this.id = -1;
    this.name = "";
    this.ean = "";
    this.price = 0;
  }

  public Product(int codigo, String name, String ean, double price) {
    this.id = codigo;
    this.name = name;
    this.ean = ean;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEan() {
    return ean;
  }

  public void setEan(String ean) {
    this.ean = ean;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getFormattedPrice() {
    return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(this.price);
  }

  @Override
  public String toString() {
    try {
      Field[] fields = Product.class.getDeclaredFields();

      String out = String.format("%s\n", this.getClass().getName());

      for (Field field : fields)
        out += String.format("%s(%s): %s\n", field.getName(), field.getType().toString(), field.get(this));

      return out;
    } catch (IllegalAccessException e) {
      System.err.println(e);

      return "Error";
    }
  }
}
