package pizza.app.database;

import pizza.app.models.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PizzaOrderDatabase {

	// Attributes.
	private Database database;

	public PizzaOrderDatabase() {
		this.database = Database.getInstance();
	}

	public void insert(Order order) {
		database.execute("insert into pizzaorder (customerName, mobileNumber, pizzaSize, numberOfToppings" +
				", totalBill) values ('" + order.getCustomerName()
				+ "', '" + order.getMobileNumber() + "', '" + order.getPizzaSize() + "', " +
				order.getNumberOfToppings() + ", "+order.getTotalBill()+")");
	}

	public Map<Integer, Order> readOrders() {
		Map<Integer, Order> orders = new HashMap<>();
		ResultSet result = database.executeQuery("select * from pizzaorder");
		try {
			while (result.next()) {
				int id = result.getInt("orderid");
				orders.put(id, new Order(id, result.getString("customerName"), result.getString("mobileNumber"),
						result.getString("pizzaSize"), result.getInt("numberOfToppings"),
						result.getDouble("totalBill")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	public void update(int orderId, Order order) {
		database.execute("update pizzaorder set customerName = '" + order.getCustomerName() + "', mobileNumber = '"
				+ order.getMobileNumber() + "', pizzaSize = '" + order.getPizzaSize() + "', numberOfToppings = "
				+ order.getNumberOfToppings() + ", totalBill = "+order.getTotalBill()+" where orderid = " + orderId);
	}

	public void delete(int orderId) {
		database.execute("delete from pizzaorder where orderid = " + orderId);
	}
}
