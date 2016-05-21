package hu.unideb.inf.server.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Ticket {
	@Id
	@GeneratedValue
	private Integer id;
	private Integer typeID;
	private User player;
	private List<Integer> fields;
	private LocalDateTime date;
	private Integer drawID;
	private Integer hits;

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public Integer getTypeID() {
		return typeID;
	}

	public void setTypeID(Integer typeID) {
		this.typeID = typeID;
	}

	public Ticket() {}

	public User getPlayer() {
		return player;
	}

	public void setPlayer(User player) {
		this.player = player;
	}

	public List<Integer> getFields() {
		return fields;
	}

	public void setFields(List<Integer> fields) {
		this.fields = fields;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Integer getDrawID() {
		return drawID;
	}

	public void setDrawID(Integer drawID) {
		this.drawID = drawID;
	}	
}
