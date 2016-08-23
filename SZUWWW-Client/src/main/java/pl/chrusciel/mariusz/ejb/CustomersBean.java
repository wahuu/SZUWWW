package pl.chrusciel.mariusz.ejb;

import java.util.List;

import javax.ejb.Local;

import pl.chrusciel.mariusz.entities.Customer;

@Local
public interface CustomersBean {
	public void add(Customer customer);

	public void delete(Customer customer);

	public List<Customer> getAll();

	public void update(Customer customer);

}
