package database;

import java.math.BigInteger;
import java.sql.*;

public class DatabaseCustomerQuery {

	private Connection connection;
	private PreparedStatement stmt;
	private ResultSet rs;

    public DatabaseCustomerQuery(Connection connection) {
        this.connection = connection;
    }

    public void addNewCustomer(long customerID, String name, String address, String email) {
        try {
            stmt = connection.prepareStatement(
                    "SELECT COUNT(*) FROM appliances.customer WHERE customer_id=" + customerID + ";");
            rs = stmt.executeQuery();
            rs.next();
            System.out.println(rs.getInt(1));
            if(rs.getInt(1) == 0) {
                stmt = connection.prepareStatement("INSERT INTO appliances.customer(customer_id, name, address, email) "
                        + "VALUES (" + customerID + ", '" + name + "', '" + address + "', '" + email + "');");
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	public Object[][] getCustomerPurchases(long id) {
		int count = 0;
		try {
			stmt = connection.prepareStatement(
					"SELECT COUNT(*) FROM appliances.customer_purchase WHERE customer_id=" + id + ";");
			rs = stmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
			System.out.println(count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Object[][] purchases = new Object[count][6];

		try {
			stmt = connection.prepareStatement(
					"SELECT appliances.customer_purchase.date, appliances.product.type, "
					+ "appliances.product.model, appliances.product.manufacturer, "
					+ "appliances.customer_purchase.quantity, appliances.customer_purchase.price "
					+ "FROM (appliances.product JOIN appliances.customer_purchase "
					+ "ON appliances.product.id=appliances.customer_purchase.product_id) "
					+ "WHERE customer_id=" + id + ";");
			rs=stmt.executeQuery();

			for (int row = 0; row < purchases.length; row++) {
				if (rs.next()) {
					for (int col = 0; col < purchases[row].length; col++) {
						purchases[row][col] = rs.getObject(col + 1);
//						System.out.println(purchases[row][col]);
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return purchases;
	}

	public boolean customerPurchase(int productId, long customerId, int quantity) {
		int totalStock = 0;
        int quantityBought = quantity;

		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement("SELECT SUM(appliances.product_unit.stock) "
					+ "FROM appliances.product_unit WHERE appliances.product_unit.product_id = " + productId + " "
			        + "GROUP BY appliances.product_unit.product_id;");
			ResultSet rs = stmt.executeQuery();
			rs.next();
			totalStock = rs.getInt(1);
			System.out.print(totalStock);
			
			if(quantity > totalStock) {
				connection.rollback();
				return false;
			}
			
			stmt = connection.prepareStatement("SELECT sale_price FROM appliances.product WHERE id = " + productId);
			ResultSet salePriceResults = stmt.executeQuery();
			salePriceResults.next();
			int price = salePriceResults.getInt(1);
			
			while(quantity > 0) {
				stmt = connection.prepareStatement("SELECT appliances.product_unit.id, appliances.product_unit.stock "
				        + "FROM appliances.product_unit "
						+ "WHERE appliances.product_unit.product_id = " + productId 
						+ "ORDER BY appliances.product_unit.stock DESC LIMIT 1;");
				ResultSet supplierStock = stmt.executeQuery();
				supplierStock.next();
				int productUnitID = supplierStock.getInt(1);
				int stock = supplierStock.getInt(2);
				
				if(stock > quantity) {
				    stock = quantity;
				}
				
				stmt = connection.prepareStatement("UPDATE appliances.product_unit SET "
						+ "stock = (stock - " + stock + ") "
				        + "WHERE appliances.product_unit.id = " + productUnitID + ";");
				stmt.executeUpdate();
				
				quantity -= stock;
			}
			
	        Timestamp time = new Timestamp(System.currentTimeMillis());
			
	        stmt = connection.prepareStatement("INSERT INTO appliances.customer_purchase(product_id, customer_id, quantity, price, date) "
					+ " VALUES(" + productId + "," + customerId + "," + quantityBought + "," + price +", '" + time + "');");
			boolean purchaseSuccess = stmt.execute();
			
		    connection.commit();
		    connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
			return false;
		}
		return true;
	}
}
