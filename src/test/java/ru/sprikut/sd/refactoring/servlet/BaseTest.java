package ru.sprikut.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.sprikut.sd.refactoring.database.Database;
import ru.sprikut.sd.refactoring.database.ProductDatabase;
import ru.sprikut.sd.refactoring.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.when;

public class BaseTest {
    private static final String TEST_DATABASE_NAME = "jdbc:sqlite:test.db";

    protected final Database<Product> database = new ProductDatabase(TEST_DATABASE_NAME);

    protected final StringWriter writer = new StringWriter();

    @Mock
    protected HttpServletRequest request;

    @Mock
    protected HttpServletResponse response;

    @Before
    public void createDatabase() {
        database.createIfNotExists();
    }

    @Before
    public void setUpMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @After
    public void dropDatabase() {
        database.dropIfExists();
    }

    protected void compareStrings(String a, String b) {
        Assert.assertEquals(a.strip(), b.strip());
    }
}
