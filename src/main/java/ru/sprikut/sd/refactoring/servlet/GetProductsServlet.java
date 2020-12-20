package ru.sprikut.sd.refactoring.servlet;

import ru.sprikut.sd.refactoring.database.Database;
import ru.sprikut.sd.refactoring.product.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author sprikut
 */
public class GetProductsServlet extends HttpServlet {

    private final Database<Product> database;

    public GetProductsServlet(Database<Product> database) {
        this.database = database;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> allProducts = database.selectAll();
        response.getWriter().println("<html><body>");
        for (Product product : allProducts) {
            response.getWriter().println(product.toHtml());
        }
        response.getWriter().println("</body></html>");

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
