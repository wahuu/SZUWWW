package pl.chrusciel.mariusz.converters;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import pl.chrusciel.mariusz.CDIManagedBeans.FaultForm;
import pl.chrusciel.mariusz.entities.Employee;

/**
 * Employee Converter uzywany w FaultForm do konwertowania listy pracowników po
 * wybraniu klienta
 * 
 * @author Wahuu
 *
 */
@Named
@ApplicationScoped
public class EmployeeConverter implements Converter {

	@Inject
	FaultForm ff;

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				List<Employee> employeeList = ff.getEmployeeList();
				for (Employee employee : employeeList) {
					if (employee.getId() == Integer.parseInt(value))
						return employee;
				}
				return null;
			} catch (NumberFormatException e) {
				throw new RuntimeException(e.getMessage());
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Employee) object).getId());
		} else {
			return null;
		}
	}
}
