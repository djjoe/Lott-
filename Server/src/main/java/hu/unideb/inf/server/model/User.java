package hu.unideb.inf.server.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="User")
public class User {
	@Id
	private String username;
	private String password;
	private String name;
	private String email;
	private Integer age;
	private Integer balance;
	
	public User() {}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", name=" + name + ", email="
				+ email + ", age=" + age + ", balance=" + balance + "]";
	}
}
