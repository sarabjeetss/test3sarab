package pizza.app.database;

import java.sql.*;

public class Database {

	private static final String URL = "jdbc:mysql://localhost:3306/pizza";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static Database database;
	private Statement statement;
	private Connection connection;

	public static Database getInstance() {
		if (database == null) {
			database = new Database();
		}
		return database;
	}

	public Database() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String sql) {
		try {
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void execute(String sql) {
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
