package pl.chrusciel.mariusz.ejb;

import java.util.Arrays;
import java.util.Date;

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
	public void customerTest() {
		Area area = new Area("gmina", "powiat");
		Customer customer = new Customer("imie", "Nazwisko", "ulica", "city", "23123", "ASB324234");
		customer.setArea(area);
		em.persist(area);
		em.persist(customer);
		
		
		Employee employee = new Employee("imie", "nazwisko", "login", "haslo", "tel", "dyspozytor");
		employee.setAreas(Arrays.asList(area));
		em.persist(employee);
		System.err.println("");
		
		FaultType faultType = new FaultType("typ");
		em.persist(faultType);
		
		Fault fault = new Fault("OK", new Date(), new Date(), "");
		fault.setFaultType(faultType);
		fault.setCustomer(customer);
		fault.setEmployee(employee);
		
		em.persist(fault);
		
	}

}
