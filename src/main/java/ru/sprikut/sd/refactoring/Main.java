package ru.sprikut.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.sprikut.sd.refactoring.database.Database;
import ru.sprikut.sd.refactoring.database.ProductDatabase;
import ru.sprikut.sd.refactoring.product.Product;
import ru.sprikut.sd.refactoring.servlet.AddProductServlet;
import ru.sprikut.sd.refactoring.servlet.GetProductsServlet;
import ru.sprikut.sd.refactoring.servlet.QueryServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        Database<Product> database = new ProductDatabase("jdbc:sqlite:test.db");
        database.createIfNotExists();

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(database)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(database)), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(database)), "/query");

        server.start();
        server.join();
    }
}