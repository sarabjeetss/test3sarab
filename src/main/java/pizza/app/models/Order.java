package pizza.app.models;

public class Order {

    private int orderId;
    private String customerName;
    private String mobileNumber;
    private String pizzaSize;
    private int numberOfToppings;
    private double totalBill;

    public Order(int orderId, String customerName, String mobileNumber, String pizzaSize, int numberOfToppings, double totalBill) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.pizzaSize = pizzaSize;
        this.numberOfToppings = numberOfToppings;
        this.totalBill = totalBill;
    }

    public Order(String customerName, String mobileNumber, String pizzaSize, int numberOfToppings) {
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.pizzaSize = pizzaSize;
        this.numberOfToppings = numberOfToppings;
    }

    public double calculate() {
        totalBill = 0;
        if (pizzaSize.equals("XL")){
            totalBill = 15;
        } else if (pizzaSize.equals("L")){
            totalBill = 12;
        } else if (pizzaSize.equals("M")){
            totalBill = 10;
        } else if (pizzaSize.equals("S")){
            totalBill = 8;
        }
        totalBill += (numberOfToppings * 1.5);
        // HST
        totalBill += (totalBill * 0.15);
        return totalBill;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(String pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public int getNumberOfToppings() {
        return numberOfToppings;
    }

    public void setNumberOfToppings(int numberOfToppings) {
        this.numberOfToppings = numberOfToppings;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }
}
