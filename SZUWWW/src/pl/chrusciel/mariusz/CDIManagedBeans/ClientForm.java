package pl.chrusciel.mariusz.CDIManagedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import pl.chrusciel.mariusz.ejb.CustomersBean;
import pl.chrusciel.mariusz.entities.Customer;

@Named
@SessionScoped
public class ClientForm implements Serializable {

	@Inject
	CustomersBean customerBean;

	private int id;
	private String imie;
	private String nazwisko;

	public List<Customer> getAllClients() {
		return new ArrayList<Customer>(Arrays.asList(new Customer("Imie", "nazwisko", "ulica", "miasto", "numer tel", "dowod osobisty")));
	}

	public void addCustomObjects() {
		customerBean.customerTest();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

}
