package ru.sprikut.sd.refactoring.database;

import ru.sprikut.sd.refactoring.product.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Database<T> {
    private final String databaseConnection;

    Database(String databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public abstract void createIfNotExists();

    public abstract void dropIfExists();

    public int insert(T object) {
        return insert(List.of(object));
    }

    public abstract int insert(List<T> objects);

    public abstract List<Product> selectAll();

    public String getDatabaseConnection() {
        return databaseConnection;
    }

    protected int execSql(String sql) {
        try (Connection c = DriverManager.getConnection(databaseConnection)) {
            try (Statement statement = c.createStatement()) {
                return statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<List<String>> selectSql(String sql, List<String> fields) {
        List<List<String>> result = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(databaseConnection)) {
            try (Statement stmt = c.createStatement()) {
                try (ResultSet resultSet = stmt.executeQuery(sql)) {
                    while (resultSet.next()) {
                        List<String> tmp = new ArrayList<>();
                        for (String field : fields) {
                            tmp.add(resultSet.getString(field));
                        }
                        result.add(tmp);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
