package com.sure;

import static spark.Spark.*;

import java.util.*;

import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

  public static void main(String[] args) {
    try {

      final int port = 80;

      port(port);

      staticFiles.location("/public");

      notFound((req, res) -> {
        Map<String, Object> model = new HashMap<>();
        return render(model, "/not-found");
      });

      internalServerError((req, res) -> {
        Map<String, Object> model = new HashMap<>();
        return render(model, "/error");
      });

      get("/", (req, res) -> {
        final Product[] products = ProductDTO.getProducts();

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("products", products);
        return render(model, "/index");
      });

      post("/", (req, res) -> {
        ProductDTO.insertProduct(new Product(0, req.queryParams("name"), req.queryParams("ean"),
            Double.parseDouble(req.queryParams("price"))));

        res.redirect("/");
        return "";
      });

      get("/update/:id", (req, res) -> {
        Product product = ProductDTO.getProduct(new Product(Integer.parseInt(req.params("id")), "", "", 0));

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("product", product);
        return render(model, "/update");
      });

      post("/update/:id", (req, res) -> {
        ProductDTO.updateProduct(new Product(Integer.parseInt(req.params("id")), req.queryParams("name"),
            req.queryParams("ean"), Double.parseDouble(req.queryParams("price"))));

        res.redirect("/");
        return "";
      });

      post("/delete/:id", (req, res) -> {
        ProductDTO.deleteProduct(new Product(Integer.parseInt(req.params("id")), "", "", 0));

        res.redirect("/");
        return "";
      });

      System.out.println(String.format("listening on http://localhost:%d", port));

    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static String render(Map<String, Object> model, String templatePath) {
    return new ThymeleafTemplateEngine().render(new ModelAndView(model, templatePath));
  }
}
