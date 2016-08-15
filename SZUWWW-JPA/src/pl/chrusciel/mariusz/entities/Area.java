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

@Entity
public class Area implements Serializable {
	@GeneratedValue
	@Id
	private int id;
	private String borought;
	private String district;
	@ManyToMany(mappedBy = "areas", fetch = FetchType.EAGER)
	private List<Employee> employees;
	@OneToOne(mappedBy = "area", fetch = FetchType.EAGER)
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

}
