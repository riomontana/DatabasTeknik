package database;

import java.sql.*;

public class DBConnection {
	
	private String driver;
	private String url;
	private String query;
	private PreparedStatement stmt;
	private ResultSet rs;
	private Connection connection;
	private String username="calendr";
	private String password="supersecurepassword";
	
	private DatabaseCustomerQuery databaseCustomerQuery;
	private DatabaseStoreQuery databaseStoreQuery;
	private DatabaseCommonQuery databaseCommonQuery;

    public DBConnection() {
		this.driver = "org.postgresql.Driver";
		this.url = "jdbc:postgresql://maxifalk.se/store?useUnicode=true&characterEncoding=utf-8";
		this.query = query;
		this.connection = connection;
		
		connectToDatabase();
	}
	
	public void connectToDatabase() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("drivrutin saknas");
			e.printStackTrace();
			return;
		}
		try {
			connection = DriverManager.getConnection(getUrl(),getUsername(),getPassword());
		} catch (SQLException e) {
			System.out.println("ingen uppkoppling mot db");
			e.printStackTrace();
			return;
		}
		if (connection != null) {
			System.out.println("du är uppkopplad");
//			try { 
//				stmt = connection.prepareStatement("SELECT * FROM appliances.customer");
//				rs = stmt.executeQuery();
//				while (rs.next()) {
//					System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3));
//				}
//			}catch (SQLException e) {
//				e.printStackTrace();
//			}
		} else {
			System.out.println("uppkopplingen misslyckades");
		}

		databaseCustomerQuery = new DatabaseCustomerQuery(connection);
        databaseStoreQuery = new DatabaseStoreQuery(connection);
        databaseCommonQuery = new DatabaseCommonQuery(connection);
	}
	
	public DatabaseCustomerQuery getDatabaseCustomerQuery() {
	    return this.databaseCustomerQuery;
	}
    
    public DatabaseStoreQuery getDatabaseStoreQuery() {
        return this.databaseStoreQuery;
    }
    
    public DatabaseCommonQuery getDatabaseCommonQuery() {
        return this.databaseCommonQuery;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

	public String getDriver(){
		return this.driver;
	}
	public String getUrl(){
		return this.url;
	}
	public String getQuery() {
		return this.query;
	}
	public Connection getConnection() {
		return this.connection; 
	}
}
