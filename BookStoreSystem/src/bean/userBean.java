package bean;

import java.io.Serializable;

public class userBean implements Serializable {
	private String name;
	private String password;
	private double balance;
	private int sign=1;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public boolean validatePassword(String password) {
		if(this.password.equals(password)) {
			return true;
		}
		return false;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance=balance;
	}
	public int getSign() {
		return sign;
	}
	public void setSign(int sign) {
		this.sign=sign;
	}
}
