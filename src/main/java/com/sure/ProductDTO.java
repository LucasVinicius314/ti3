package com.sure;

public class ProductDTO {
  public static boolean insertProduct(Product product) {
    try {
      DAO.execute(String.format("insert into product (name, ean, price) values ('%s', '%s', '%f')", product.getName(),
          product.getEan(), product.getPrice()));
      return true;
    } catch (Exception e) {
      System.err.println(e);
      return false;
    }
  }

  public static boolean updateProduct(Product product) {
    try {
      DAO.execute(String.format("update product set name = '%s', ean = '%s', price = '%f' where id = '%d'",
          product.getName(), product.getEan(), product.getPrice(), product.getId()));
      return true;
    } catch (Exception e) {
      System.err.println(e);
      return false;
    }
  }

  public static boolean deleteProduct(Product product) {
    try {
      DAO.execute(String.format("delete from product where id = '%d'", product.getId()));
      return true;
    } catch (Exception e) {
      System.err.println(e);
      return false;
    }
  }

  public static Product getProduct(Product product) {
    try {
      return DAO.getProduct(String.format("select * from product where id = '%d'", product.getId()));
    } catch (Exception e) {
      System.err.println(e);
      return null;
    }
  }

  public static Product[] getProducts() {
    try {
      return DAO.getProducts("select * from product");
    } catch (Exception e) {
      System.err.println(e);
      return new Product[0];
    }
  }
}