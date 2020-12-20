package ru.sprikut.sd.refactoring.servlet;

import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

public class AddProductTest extends BaseTest {

    @Test
    public void emptyTest() throws IOException {
        new GetProductsServlet().doGet(request, response);
        compareStrings("<html><body>\n</body></html>", writer.toString());
    }

    @Test
    public void notEmptyTest() throws SQLException, IOException {
        execSql("INSERT INTO PRODUCT(NAME, PRICE) VALUES ('name1', 1), ('name2', 2)");
        new GetProductsServlet().doGet(request, response);
        compareStrings("<html><body>\nname1\t1</br>\nname2\t2</br>\n</body></html>", writer.toString());
    }
}