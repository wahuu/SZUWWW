package pl.chrusciel.mariusz.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
