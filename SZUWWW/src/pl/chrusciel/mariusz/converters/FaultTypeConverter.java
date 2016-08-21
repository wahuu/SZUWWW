package pl.chrusciel.mariusz.converters;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import pl.chrusciel.mariusz.CDIManagedBeans.CustomerForm;
import pl.chrusciel.mariusz.CDIManagedBeans.FaultForm;
import pl.chrusciel.mariusz.entities.Fault;
import pl.chrusciel.mariusz.entities.FaultType;
/**
 * FaultType Converter uzywany w FaultForm
 * @author Wahuu
 *
 */
@Named
@ApplicationScoped
public class FaultTypeConverter implements Converter {

	@Inject
	FaultForm ff;

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				List<FaultType> allFaults = ff.getAllFaultTypes();
				for (FaultType faultType : allFaults) {
					if (faultType.getId() == Integer.parseInt(value))
						return faultType;
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
			return String.valueOf(((FaultType) object).getId());
		} else {
			return null;
		}
	}
}
