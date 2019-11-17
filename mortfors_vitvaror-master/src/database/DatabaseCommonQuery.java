package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseCommonQuery {
    private Connection connection;
    private PreparedStatement stmt;
    private ResultSet rs;

    public DatabaseCommonQuery(Connection connection) {
        this.connection = connection;
    }

    public Object[][] getProducts() {
        int count = 0;
        try {
            stmt = connection.prepareStatement("SELECT COUNT(*) FROM appliances.product;");
            rs = stmt.executeQuery();
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] products = new Object[count][6];
        try {
            stmt = connection.prepareStatement("SELECT appliances.product.id, appliances.product.type,"
                    + "appliances.product.model, appliances.product.manufacturer, appliances.product.sale_price, "
                    + "SUM(COALESCE(appliances.product_unit.stock, 0)) FROM "
                    + "(appliances.product LEFT JOIN appliances.product_unit ON product.id=product_unit.product_id) "
                    + "GROUP BY appliances.product.id ORDER BY appliances.product.id ASC;");
            rs = stmt.executeQuery();

            for (int row = 0; row < products.length; row++) {
                if (rs.next()) {
                    for (int col = 0; col < products[0].length; col++) {
                        products[row][col] = rs.getObject(col + 1);
                        System.out.println(products[row][col]);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Object[][] searchProducts(String choice, String search) {
        int count = 0;
        try {
            stmt = connection
                    .prepareStatement("SELECT COUNT(*) FROM appliances.product WHERE " + choice + " LIKE '%" + search + "%';");
            rs = stmt.executeQuery();
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] products = new Object[count][6];
        try {
            stmt = connection.prepareStatement("SELECT appliances.product.id, appliances.product.type, "
                    + "appliances.product.model, appliances.product.manufacturer, appliances.product.sale_price, "
                    + "SUM(COALESCE(appliances.product_unit.stock, 0)) FROM "
                    + "(appliances.product LEFT JOIN appliances.product_unit ON product.id = product_unit.product_id) "
                    + "WHERE appliances.product." + choice + " LIKE '%" + search + "%' "
                    + "GROUP BY appliances.product." + choice + ", appliances.product.id "
                    + "ORDER BY appliances.product.id;");
            rs = stmt.executeQuery();

            for (int row = 0; row < products.length; row++) {
                if (rs.next()) {
                    for (int col = 0; col < products[row].length; col++) {
                        products[row][col] = rs.getObject(col + 1);
                        System.out.println(products[row][col]);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
