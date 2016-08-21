package pl.chrusciel.mariusz.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pl.chrusciel.mariusz.entities.Area;
import pl.chrusciel.mariusz.entities.Customer;
import pl.chrusciel.mariusz.entities.Employee;

@Named
@Stateless
public class EmployeesBeanImpl implements EmployeesBean {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(Employee employee) {
		em.persist(employee);
	}

	@Override
	public void delete(Employee employee) {
		employee = em.merge(employee);
		em.remove(employee);
	}

	@Override
	public List<Employee> getAll() {
		List<Employee> resultList = em.createQuery("SELECT e from Employee e", Employee.class).getResultList();
		return resultList;
	}

	@Override
	public void update(Employee employee) {
		em.merge(employee);
	}

	@Override
	public List<Employee> getByArea(Area area) {
		TypedQuery<Employee> query = em.createQuery("Select e from Employee e inner join e.areas a where a = :area", Employee.class);
		query.setParameter("area", area);
		return query.getResultList();
	}

}
