package ru.sprikut.sd.refactoring.servlet;

import org.junit.Test;
import ru.sprikut.sd.refactoring.product.Product;

import java.io.IOException;
import java.util.List;

public class GetProductTest extends BaseTest {

    @Test
    public void emptyGetTest() throws IOException {
        new GetProductsServlet(database).doGet(request, response);
        compareStrings("<html><body>\n</body></html>", writer.toString());
    }

    @Test
    public void notEmptyGetTest() throws IOException {
        database.insert(List.of(new Product("name1", 1),
                new Product("name2", 2)));
        new GetProductsServlet(database).doGet(request, response);
        compareStrings("<html><body>\nname1\t1</br>\nname2\t2</br>\n</body></html>", writer.toString());
    }
}