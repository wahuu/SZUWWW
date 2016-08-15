package pl.chrusciel.mariusz.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FaultType implements Serializable{
	@GeneratedValue
	@Id
	private int id;
	private String type;

	public FaultType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FaultType(String type) {
		super();
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
