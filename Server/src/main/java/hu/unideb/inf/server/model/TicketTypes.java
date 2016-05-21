package hu.unideb.inf.server.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TicketTypes {
	@Id
	private Integer typeID;
	private Integer fullBalance;
	private String typeName;
	public Integer getTypeID() {
		return typeID;
	}
	public void setTypeID(Integer typeID) {
		this.typeID = typeID;
	}
	public Integer getFullBalance() {
		return fullBalance;
	}
	public void setFullBalance(Integer fullBalance) {
		this.fullBalance = fullBalance;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public TicketTypes() {}
	
	
}
