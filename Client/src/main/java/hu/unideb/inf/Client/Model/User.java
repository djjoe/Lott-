package hu.unideb.inf.Client.Model;

public class User {
	String userName;
	String email;
	String name;
	Integer age;
	Integer balance;
	
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public User(String userName, String email, String name, Integer age, Integer balance) {
		this.userName = userName;
		this.email = email;
		this.name = name;
		this.age = age;
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", email=" + email + ", name=" + name + ", age=" + age + ", balance="
				+ balance + "]";
	}
}
