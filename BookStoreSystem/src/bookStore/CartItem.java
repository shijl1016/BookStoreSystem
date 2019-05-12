package bookStore;

public class CartItem {
	private String name;
	private int amount;
	private double price;
	private int id;
	public CartItem(int id, String name, int amount, double price) {
		this.id = id;
		this.name = name;
		this.amount=amount;
		this.price = price;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getAmount() {
		return amount;
	}
	public double getPrice() {
		return price;
	}
	public void setAmount(int amount) {
		this.amount=amount;
	}
	public double getSum() {
		return this.amount * this.price;
	}
}
