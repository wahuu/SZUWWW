package pl.chrusciel.mariusz.ejb;

import java.util.List;

import pl.chrusciel.mariusz.entities.Area;
import pl.chrusciel.mariusz.entities.Employee;

public interface EmployeesBean {
	public void add(Employee employee);

	public void delete(Employee employee);

	public List<Employee> getAll();

	public void update(Employee employee);

	public List<Employee> getByArea(Area area);
}
