package ru.sprikut.sd.refactoring.servlet;

import ru.sprikut.sd.refactoring.builder.HtmlBuilder;
import ru.sprikut.sd.refactoring.database.Database;
import ru.sprikut.sd.refactoring.product.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sprikut
 */
public class QueryServlet extends HttpServlet {
    private final Database<Product> database;

    public QueryServlet(Database<Product> database) {
        this.database = database;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if (command == null) {
            response.getWriter().println("Unknown command: null");
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        HtmlBuilder htmlBuilder = new HtmlBuilder();
        switch (command) {
            case "max":
                htmlBuilder.appendH1Tag("Product with max price: ");
                Product maxProduct = database.max();
                if (maxProduct != null) {
                    htmlBuilder.appendText(maxProduct.toHtml());
                }
                break;
            case "min":
                htmlBuilder.appendH1Tag("Product with min price: ");
                Product minProduct = database.min();
                if (minProduct != null) {
                    htmlBuilder.appendText(minProduct.toHtml());
                }
                break;
            case "sum":
                htmlBuilder.appendText("Summary price: ");
                int sum = database.sum();
                htmlBuilder.appendText(String.valueOf(sum));
                break;
            case "count":
                htmlBuilder.appendText("Number of products: ");
                int count = database.count();
                htmlBuilder.appendText(String.valueOf(count));
                break;
            default:
                htmlBuilder.clear();
                htmlBuilder.appendText("Unknown command: " + command);
        }
        response.getWriter().println(htmlBuilder.toString());
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}