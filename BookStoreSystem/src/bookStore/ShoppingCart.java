package bookStore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
public class ShoppingCart implements Serializable {
	private ArrayList<CartItem> cart;

	public ShoppingCart() {
		cart = new ArrayList<CartItem>();
	}
	public ArrayList<CartItem> getCart() {
		return cart;
	}
	public void addCartItem(CartItem item) {
		CartItem oldItem = null;
		if (item != null) {
			for (int i = 0; i < cart.size(); i++) {
				oldItem = cart.get(i);
				if (oldItem.getId()==item.getId()) {
					oldItem.setAmount(oldItem.getAmount() + item.getAmount());
					return;
				}
			}
			cart.add(item);
		}
	}
	public boolean removeCartItem(int id) {
		CartItem item = null;
		for (int i = 0; i < cart.size(); i++) {
			item = cart.get(i);
			if (item.getId()==id) {
				cart.remove(i);
				return true;
			}
		}
		return false;
	}

	public double getTotal() {
		Iterator<CartItem> it = cart.iterator();
		double sum = 0.0;
		CartItem item = null;
		while (it.hasNext()) {
			item = it.next();
			sum = sum + item.getSum();
		}
		return sum;
	}
	public CartItem getCartItem(int id) {
		CartItem item = null;
		for (int i = 0; i < cart.size(); i++) {
			item = cart.get(i);
			if (item.getId()==id) {
				return item;
			}
		}
		return null;
	}
}
