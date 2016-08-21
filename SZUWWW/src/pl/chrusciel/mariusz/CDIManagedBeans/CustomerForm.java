package pl.chrusciel.mariusz.CDIManagedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;

import pl.chrusciel.mariusz.ejb.AreaBean;
import pl.chrusciel.mariusz.ejb.CustomersBean;
import pl.chrusciel.mariusz.entities.Area;
import pl.chrusciel.mariusz.entities.Customer;

@Named
@SessionScoped
public class CustomerForm implements Serializable {

	@Inject
	private CustomersBean customerBean;

	@Inject
	AreaBean areaBean;

	private Customer modifyCustomer;

	private List<Customer> allCustomers;
	private List<Customer> filteredCustomers;
	private List<Area> allAreas;

	@PostConstruct
	public void init() {
		this.modifyCustomer = new Customer();
		updateAllCustomers();
		allAreas = areaBean.getAll();
	}

	private void updateAllCustomers() {
		this.allCustomers = customerBean.getAll();
	}

	public String goToAddNew() {
		this.modifyCustomer = new Customer();
		return "/forms/customersModify?faces-redirect=true";
	}

	public String addNew() {
		customerBean.add(this.modifyCustomer);
		this.modifyCustomer = new Customer();
		updateAllCustomers();
		return "/forms/customers?faces-redirect=true";
	}

	public String goToModify(Customer customer) {
		this.modifyCustomer = customer;
		return "/forms/customersModify?faces-redirect=true";
	}

	public String modify() {
		customerBean.update(this.modifyCustomer);
		updateAllCustomers();
		return "/forms/customers?faces-redirect=true";
	}

	public String delete(Customer customer) {
		customerBean.delete(customer);
		updateAllCustomers();
		return "/forms/customers?faces-redirect=true";
	}

	/*
	 * public void onSelect(SelectEvent event) { Object object =
	 * event.getObject(); this.modifyCustomer.setArea((Area) object); }
	 */

	public Customer getModifyCustomer() {
		return modifyCustomer;
	}

	public void setModifyCustomer(Customer modifyCustomer) {
		this.modifyCustomer = modifyCustomer;
	}

	public List<Customer> getAllCustomers() {
		return allCustomers;
	}

	public void setAllCustomers(List<Customer> allCustomers) {
		this.allCustomers = allCustomers;
	}

	public List<Customer> getFilteredCustomers() {
		return filteredCustomers;
	}

	public void setFilteredCustomers(List<Customer> filteredCustomers) {
		this.filteredCustomers = filteredCustomers;
	}

	public List<Area> getAllAreas() {
		return allAreas;
	}

	public void setAllAreas(List<Area> allAreas) {
		this.allAreas = allAreas;
	}

}
