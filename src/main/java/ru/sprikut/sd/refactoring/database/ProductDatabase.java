package ru.sprikut.sd.refactoring.database;

import ru.sprikut.sd.refactoring.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDatabase extends Database<Product> {

    public ProductDatabase(String databaseConnection) {
        super(databaseConnection);
    }

    @Override
    public void createIfNotExists() {
        execSql("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME TEXT NOT NULL, PRICE INT NOT NULL)");
    }

    @Override
    public void dropIfExists() {
        execSql("DROP IF EXISTS PRODUCT");
    }

    @Override
    public int insert(List<Product> objects) {
        StringBuilder builder = new StringBuilder("INSERT INTO PRODUCT (NAME, PRICE) VALUES");
        for (int i = 0; i < objects.size(); i++) {
            builder.append(objects.get(i).toSql());
            if (i != objects.size() - 1) {
                builder.append(", ");
            }
        }
        return execSql(builder.toString());
    }

    private Product parseProduct(List<String> obj) {
        return new Product(obj.get(0), Long.parseLong(obj.get(1)));
    }

    @Override
    public List<Product> selectAll() {
        List<List<String>> select = selectSql("SELECT NAME, PRICE FROM PRODUCT", List.of("NAME", "PRICE"));
        return select.stream().map(this::parseProduct).collect(Collectors.toList());
    }

    private Product minMax(int mode) {
        List<Product> products = selectAll();
        if (products.isEmpty()) {
            return null;
        }
        Product answer = products.get(0);
        for (Product product : products) {
            if (mode == 0 && answer.getPrice() > product.getPrice() ||
                    mode == 1 && answer.getPrice() < product.getPrice()) {
                answer = product;
            }
        }
        return answer;
    }

    @Override
    public Product max() {
        return minMax(1);
    }

    @Override
    public Product min() {
        return minMax(0);
    }

    @Override
    public int sum() {
        List<Product> products = selectAll();
        int sum = 0;
        for (Product product : products) {
            sum += product.getPrice();
        }
        return sum;
    }

    @Override
    public int count() {
        List<Product> products = selectAll();
        return products.size();
    }
}