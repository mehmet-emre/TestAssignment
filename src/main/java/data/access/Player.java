package data.access;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PLAYER")
public class Player{

	@Id
	private String userName;
	
	private int balance_Version;
	private double balance;

	public Player() {
		super();
	}
	public Player(String userName, int balance_Version, double balance) {
		super();
		this.userName = userName;
		this.balance_Version = balance_Version;
		this.balance = balance;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getBalance_Version() {
		return balance_Version;
	}
	public void setBalance_Version(int balance_Version) {
		this.balance_Version = balance_Version;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
}
