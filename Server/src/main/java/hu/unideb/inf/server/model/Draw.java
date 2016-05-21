package hu.unideb.inf.server.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Draw {
	
	@Id
	private Integer id;
	private Timestamp date;
	private Integer ticketType;
	@ElementCollection
	private List<Integer> fields;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Draw() {}
	
	public LocalDateTime getDate() {
		return date.toLocalDateTime();
	}
	
	public void setDate(LocalDateTime date) {
		this.date = Timestamp.valueOf(date);
	}
	
	public Integer getTicketType() {
		return ticketType;
	}
	
	public void setTicketType(Integer ticketType) {
		this.ticketType = ticketType;
	}
	
	public List<Integer> getFields() {
		return fields;
	}
	
	public void setFields(List<Integer> fields) {
		this.fields = fields;
	}
}
