package pl.chrusciel.mariusz.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Fault implements Serializable {
	@GeneratedValue
	@Id
	private int id;
	private String status;
	private Date filingDate;
	private Date endDate;
	private String comment;
	@OneToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id")
	private Employee employee;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "faultType_id")
	private FaultType faultType;

	public Fault() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Fault(String status, Date filingDate, Date endDate, String comment) {
		super();
		this.status = status;
		this.filingDate = filingDate;
		this.endDate = endDate;
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getFilingDate() {
		return filingDate;
	}

	public void setFilingDate(Date filingDate) {
		this.filingDate = filingDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public FaultType getFaultType() {
		return faultType;
	}

	public void setFaultType(FaultType faultType) {
		this.faultType = faultType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Fault other = (Fault) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
