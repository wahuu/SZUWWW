package pl.chrusciel.mariusz.CDIManagedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import pl.chrusciel.mariusz.ejb.AreaBean;
import pl.chrusciel.mariusz.ejb.EmployeesBean;
import pl.chrusciel.mariusz.entities.Area;
import pl.chrusciel.mariusz.entities.Employee;

@Named
@SessionScoped
public class EmployeeForm implements Serializable {

	@Inject
	EmployeesBean employeesBean;

	@Inject
	AreaBean areaBean;

	private Employee modifyEmployee;
	private List<Employee> allEmployees;
	private DualListModel<Area> areasModel;
	private List<Area> allAreas;

	@PostConstruct
	public void init() {
		modifyEmployee = new Employee();
		updateAllEmployees();
		allAreas = areaBean.getAll();
		initDualListModel(allAreas, new ArrayList<Area>());
	}
	
	public void updateAllEmployees() {
		this.allEmployees = employeesBean.getAll();
	}

	private void initDualListModel(List<Area> source, List<Area> target) {
		List<Area> areasSource = source;
		List<Area> areasTarget = target;
		areasModel = new DualListModel<>(areasSource, areasTarget);
	}

	public String goToAddNew() {
		this.modifyEmployee = new Employee();
		initDualListModel(allAreas, new ArrayList<Area>());
		return "/forms/employeesModify?faces-redirect=true";
	}

	public String addNew() {
		this.modifyEmployee.setAreas(areasModel.getTarget());
		employeesBean.add(this.modifyEmployee);
		this.modifyEmployee = new Employee();
		updateAllEmployees();
		return "/forms/employees?faces-redirect=true";
	}

	public String goToModify(Employee employee) {
		this.modifyEmployee = employee;
		deleteRepeatedAreas(employee.getAreas());
		// initDualListModel(employee.getAreas());
		return "/forms/employeesModify?faces-redirect=true";
	}

	public String modify() {
		this.modifyEmployee.setAreas(areasModel.getTarget());
		employeesBean.update(this.modifyEmployee);
		updateAllEmployees();
		return "/forms/employees?faces-redirect=true";
	}

	public String delete(Employee employee) {
		employeesBean.delete(employee);
		updateAllEmployees();
		return "/forms/employees?faces-redirect=true";
	}

	public Employee getModifyEmployee() {
		return modifyEmployee;
	}

	public void setModifyEmployee(Employee modifyEmployee) {
		this.modifyEmployee = modifyEmployee;
	}

	public DualListModel<Area> getAreasModel() {
		return areasModel;
	}

	public void setAreasModel(DualListModel<Area> areasModel) {
		this.areasModel = areasModel;
	}

	public List<Employee> getAllEmployees() {
		return allEmployees;
	}

	public void setAllEmployees(List<Employee> allEmployees) {
		this.allEmployees = allEmployees;
	}

	private void deleteRepeatedAreas(List<Area> employeeAreas) {
		List<Area> sourceAreas = new ArrayList<Area>();
		sourceAreas.addAll(allAreas);
		for (Area area : employeeAreas) {
			for (Area sourceArea : sourceAreas) {
				if (area.getId() == sourceArea.getId())
					sourceAreas.remove(sourceArea);
				break;
			}

		}
		initDualListModel(sourceAreas, employeeAreas);
	}

}
