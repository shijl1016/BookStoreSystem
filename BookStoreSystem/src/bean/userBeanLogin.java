package bean;

import java.io.Serializable;
import java.util.Hashtable;
public class userBeanLogin implements Serializable {
	private String name = "";
	private String password = "";
	private Hashtable errors = new Hashtable();
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getPassword()
	{
		return this.password;
	}
	public boolean validate()
	{
		boolean allOk = true;
		if(name.trim().equals(""))
		{
			errors.put("name","Please input your name.");
			allOk = false;
		}
		if(password.length()>10 || password.length()<6)
		{
			errors.put("password","password must have 6-10 characters.");
			allOk = false;
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
