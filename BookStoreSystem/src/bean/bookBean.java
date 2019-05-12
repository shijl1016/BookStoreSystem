package bean;

import java.io.Serializable;
import java.util.Hashtable;

public class bookBean implements Serializable {
	private String name="";
	private String amount="";
	private int mAmount=0;
	private String price="";
	private float mPrice=0;
	private Hashtable errors = new Hashtable();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount=amount;
	}
	public int getMAmount() {
		return mAmount;
	}
	public void setMAmount(int mAmount) {
		this.mAmount=mAmount;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price=price;
	}
	public float getMPrice() {
		return mPrice;
	}
	public void setMPrice(float mPrice) {
		this.mPrice=mPrice;
	}
	public boolean validate() {
		boolean allOk=true;
		if(name.trim().equals(""))
		{
			errors.put("name","Please input the title of a book.");
			allOk = false;
		}
		try {
			mAmount=Integer.parseInt(amount);
			if(mAmount<0) {
				errors.put("amount","Please input a positive interger." );
				allOk=false;
			}
		}catch(NumberFormatException e){
			errors.put("amount", "Please input a positive interger.");
			allOk=false;
		}
		try {
			mPrice=Float.parseFloat(price);
			if(mPrice<0) {
				allOk=false;
				errors.put("price", "Please input a positive and 2-bit decimal number.");
			}else if((price.length()-1-price.indexOf('.'))>2){
				allOk=false;
				errors.put("price", "Please input a positive and 2-bit decimal number.");
			}
		}catch(NumberFormatException e) {
			allOk=false;
			errors.put("price", "Please input a positive and 2-bit decimal number.");
		}
		return allOk;
	}
	public String getErrorMsg(String err)
	{
		String err_msg = (String)errors.get(err);
		return (err_msg == null) ? "" : err_msg;
	}
	public void setErrorMsg(String err,String errMsg)
	{
		if((err != null) && (errMsg != null))
		{
			errors.put(err,errMsg);
		}
	}
}
