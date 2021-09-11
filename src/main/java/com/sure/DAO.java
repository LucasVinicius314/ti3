package com.sure;

import java.sql.*;

public class DAO {
  // TODO: configure database before running
  static String server = "localhost";
  static String defaultDatabase = "postgres";
  static String username = "postgres";
  static String password = "1234";
  static int port = 5432;

  static String driverName = "org.postgresql.Driver";
  static String url = String.format("jdbc:postgresql://%s:%d/%s", server, port, defaultDatabase);

  static Connection connect() throws Exception {
    try {
      Class.forName(driverName);
      Connection connection = DriverManager.getConnection(url, username, password);

      System.out.println("Connected");

      return connection;
    } catch (ClassNotFoundException e) {
      System.err.println(e);

      throw e;
    } catch (SQLException e) {
      System.err.println(e);

      throw e;
    }
  }

  public static void execute(String sql) throws Exception {
    Connection connection = connect();

    try {
      Statement st = connection.createStatement();
      st.execute(sql);
      st.close();
      connection.close();
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }
  }

  public static Product getProduct(String sql) throws Exception {
    Product product = null;
    Connection connection = connect();

    try {
      Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = st.executeQuery(sql);

      if (rs.first())
        product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("ean"), rs.getDouble("price"));

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return product;
  }

  public static Product[] getProducts(String sql) throws Exception {
    Product[] products = new Product[0];
    Connection connection = connect();

    try {
      Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = st.executeQuery(sql);

      if (rs.next()) {
        rs.last();
        products = new Product[rs.getRow()];
        rs.beforeFirst();

        for (int i = 0; rs.next(); i++)
          products[i] = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("ean"), rs.getDouble("price"));
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return products;
  }
}