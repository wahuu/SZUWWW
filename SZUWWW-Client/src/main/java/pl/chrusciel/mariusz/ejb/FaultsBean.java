package pl.chrusciel.mariusz.ejb;

import java.util.Date;
import java.util.HashMap;
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
	
	/**
	 * Dane dla wykresu kolowego usterki ze względu na typ usterki miedzy datami.
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<HashMap<String, Long>> countFaultsForFaultTypes(Date dateFrom, Date dateTo);
	
	/**
	 * Dane dla wykresu liniowy ilosc usterek zgloszonych miedzy datami dateFrom a dateTo ze względu na typ usterki.
	 * @param dateFrom
	 * @param dateTo
	 */
	public List<HashMap<String, Object>> countFaults(Date dateFrom, Date dateTo);
}
