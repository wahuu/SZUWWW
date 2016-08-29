package pl.chrusciel.mariusz.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Area implements Serializable {
	@GeneratedValue
	@Id
	private int id;
	private String borought;
	private String district;
	@ManyToMany(mappedBy = "areas", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Employee> employees;
	@OneToOne(mappedBy = "area", fetch = FetchType.EAGER)
	@JsonIgnore
	private Customer customer;

	public Area() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Area(String borought, String district) {
		super();
		this.borought = borought;
		this.district = district;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBorought() {
		return borought;
	}

	public void setBorought(String borought) {
		this.borought = borought;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((borought == null) ? 0 : borought.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Area other = (Area) obj;
		if (borought == null) {
			if (other.borought != null)
				return false;
		} else if (!borought.equals(other.borought))
			return false;
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

}
