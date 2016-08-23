package pl.chrusciel.mariusz.ejb;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.chrusciel.mariusz.ejb.CustomersBean;
import pl.chrusciel.mariusz.entities.Area;
import pl.chrusciel.mariusz.entities.Customer;
import pl.chrusciel.mariusz.entities.Employee;
import pl.chrusciel.mariusz.entities.Fault;
import pl.chrusciel.mariusz.entities.FaultType;

/**
 * Session Bean implementation class CustomersBeanImpl
 */
@Named
@Stateless
public class CustomersBeanImpl implements CustomersBean {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(Customer customer) {
		em.persist(customer);
	}

	@Override
	public void delete(Customer customer) {
		customer = em.merge(customer);
		em.remove(customer);
	}

	@Override
	public List<Customer> getAll() {
		List<Customer> resultList = em.createQuery("SELECT c from Customer c", Customer.class).getResultList();
		return resultList;
	}

	@Override
	public void update(Customer customer) {
		em.merge(customer);
	}

}
