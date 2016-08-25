package pl.chrusciel.mariusz.ejb;

import java.util.List;

import pl.chrusciel.mariusz.entities.Employee;
import pl.chrusciel.mariusz.entities.Fault;

public interface FaultsBean {
	public void add(Fault fault);

	public void delete(Fault fault);

	public List<Fault> getAll();

	public void update(Fault fault);
	
	public List<Fault> getByStatus(List<String> statusList);
	
	public List<Fault> getByStatusAndEmployee(List<String> statusList, Employee employee);
}
