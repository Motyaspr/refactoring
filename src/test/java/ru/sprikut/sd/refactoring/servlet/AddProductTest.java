package ru.sprikut.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Test;
import ru.sprikut.sd.refactoring.product.Product;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.when;

public class AddProductTest extends BaseTest {

    @Test(expected = Exception.class)
    public void emptyAddTest() throws IOException {
        new AddProductServlet(database).doGet(request, response);
    }


    @Test
    public void simpleAddTest() throws IOException {
        when(request.getParameter("name")).thenReturn("my_name");
        when(request.getParameter("price")).thenReturn("23");
        new AddProductServlet(database).doGet(request, response);
        compareStrings("OK", writer.toString());

        List<Product> all = database.selectAll();
        Assert.assertEquals(all.size(), 1);
        Assert.assertEquals(all.get(0).getName(), "my_name");
        Assert.assertEquals(all.get(0).getPrice(), 23);
    }
}