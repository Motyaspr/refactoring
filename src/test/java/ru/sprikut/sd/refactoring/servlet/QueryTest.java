package ru.sprikut.sd.refactoring.servlet;

import org.junit.Test;
import ru.sprikut.sd.refactoring.product.Product;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.when;

public class QueryTest extends BaseTest {
    private void testCommand(String command, String result) throws IOException {
        when(request.getParameter("command")).thenReturn(command);
        new QueryServlet(database).doGet(request, response);
        compareStrings(writer.toString(), result);
    }

    @Test
    public void nullCommandTest() throws IOException {
        new QueryServlet(database).doGet(request, response);
        compareStrings(writer.toString(), "Unknown command: null");
    }

    @Test
    public void badCommandTest() throws IOException {
        testCommand("testtest", "Unknown command: testtest");
    }

    private void maxTest(String result) throws IOException {
        testCommand("max", "<html><body>\n" +
                "<h1>Product with max price: </h1>\n" +
                result +
                "</body></html>");
    }

    @Test
    public void emptyMaxTest() throws IOException {
        maxTest("");
    }

    private void simpleInsert() {
        database.insert(List.of(new Product("iphone", 111), new Product("samsung", 95)));
    }

    @Test
    public void simpleMaxTest() throws IOException {
        simpleInsert();
        maxTest("iphone\t111</br>\n");
    }

    private void minTest(String result) throws IOException {
        testCommand("min", "<html><body>\n" +
                "<h1>Product with min price: </h1>\n" +
                result +
                "</body></html>");
    }

    @Test
    public void emptyMinTest() throws IOException {
        minTest("");
    }

    @Test
    public void simpleMinTest() throws IOException {
        simpleInsert();
        minTest("samsung\t95</br>\n");
    }

    private void sumTest(String result) throws IOException {
        testCommand("sum", "<html><body>\n" +
                "Summary price: \n" +
                result +
                "</body></html>");
    }

    @Test
    public void emptySumTest() throws IOException {
        sumTest("0\n");
    }

    @Test
    public void simpleSumTest() throws IOException {
        simpleInsert();
        sumTest("206\n");
    }

    private void countTest(String result) throws IOException {
        testCommand("count", "<html><body>\n" +
                "Number of products: \n" +
                result +
                "</body></html>");
    }

    @Test
    public void emptyCountTest() throws IOException {
        countTest("0\n");
    }

    @Test
    public void simpleCountTest() throws IOException {
        simpleInsert();
        countTest("2\n");
    }
}
