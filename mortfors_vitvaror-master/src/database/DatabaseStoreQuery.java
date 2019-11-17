package database;

import java.sql.*;

public class DatabaseStoreQuery {

    private Connection connection;
    private PreparedStatement stmt;
    private ResultSet rs;

    public DatabaseStoreQuery(Connection connection) {
        this.connection = connection;

        // createSupplierTransaction(1,2,5099);
    }

    public int getRowQuantity(int id, String table, String column) {
        int qty = 0;
        try {
            if (id != -1) {
                stmt = connection
                        .prepareStatement("SELECT COUNT(*) FROM " + table + " WHERE " + column + "='" + id + "';");
            } else {
                stmt = connection.prepareStatement("SELECT COUNT(*) FROM " + table + ";");
            }
            rs = stmt.executeQuery();
            rs.next();
            qty = rs.getInt(1);

        } catch (SQLException e) {
            System.out.println("Could not get number of Suppliers for id: " + id);
            e.printStackTrace();
        }
        return qty;
    }

    public Object[][] createTable(Object[][] table) {
        try {
            for (int row = 0; row < table.length; row++) {
                if (rs.next()) {
                    for (int col = 0; col < table[row].length; col++) {
                        table[row][col] = rs.getObject(col + 1);
                        // System.out.println(table[row][col]);
                    }
                } else {
                    for (int col = 0; col < table[row].length; col++) {
                        table[row][col] = null;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Could not create table");
            e.printStackTrace();
        }
        return table;
    }

    public Object[][] getSuppliers(int id) {
        int count = 0;
        String table = "appliances.product_unit";
        String column = "product_id";
        count = getRowQuantity(id, table, column);
        Object[][] suppliers = new Object[count][5];
        try {
            stmt = connection.prepareStatement(
                    "SELECT a.id, a.name, a.phone_number, a.website, appliances.product_unit.supply_price FROM "
                            + "(appliances.supplier AS a JOIN appliances.product_unit ON a.id=appliances.product_unit.supplier_id) "
                            + "WHERE appliances.product_unit.product_id='" + id + "';");
            rs = stmt.executeQuery();
            createTable(suppliers);

        } catch (SQLException e) {
            System.out.println("Could not get suppliers for id: " + id);
            e.printStackTrace();
        }
        return suppliers;
    }

    public Object[][] getTransactions(int id) {
        String table = "appliances.customer_purchase";
        String column = "product_id";
        int count = getRowQuantity(id, table, column);
        Object[][] transactions = new Object[count][5];
        try {
            stmt = connection.prepareStatement(
                    "SELECT appliances.customer.customer_id, appliances.customer.name, appliances.product.model, appliances.customer_purchase.quantity, appliances.customer_purchase.price "
                            + "FROM ((appliances.customer_purchase JOIN appliances.customer ON appliances.customer_purchase.customer_id=appliances.customer.customer_id) "
                            + "JOIN appliances.product ON appliances.customer_purchase.product_id=appliances.product.id) WHERE appliances.product.id='"
                            + id + "';");
            rs = stmt.executeQuery();
            createTable(transactions);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public Object[][] getAllCustomers() {
        int id = -1;
        String table = "appliances.customer";
        String column = "";
        int rowcount = getRowQuantity(id, table, column);

        Object[][] customers = new Object[rowcount][5];

        try {
            stmt = connection.prepareStatement(
                    "SELECT c.customer_id, c.name, c.address, c.email, SUM(appliances.customer_purchase.price*appliances.customer_purchase.quantity) FROM (appliances.customer AS c JOIN appliances.customer_purchase ON c.customer_id=appliances.customer_purchase.customer_id) GROUP BY c.customer_id;");
            rs = stmt.executeQuery();
            createTable(customers);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public boolean createSupplierTransaction(int unitId, int quantity, double price) {
        boolean answer = false;
        System.out.println("Id: " + unitId + " Quantity: " + quantity + " Price: " + price);
        try {
            stmt = connection.prepareStatement("UPDATE appliances.product_unit SET stock = stock +" + quantity
                    + ", supply_price = " + price + " WHERE appliances.product_unit.product_id=" + unitId + ";");
            stmt.executeUpdate();
            answer = true;
        } catch (SQLException e) {
            e.printStackTrace();
            answer = false;
        }
        return answer;
    }
}