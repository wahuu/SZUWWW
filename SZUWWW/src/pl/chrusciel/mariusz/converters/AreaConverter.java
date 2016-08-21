package pl.chrusciel.mariusz.converters;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import pl.chrusciel.mariusz.CDIManagedBeans.CustomerForm;
import pl.chrusciel.mariusz.entities.Area;

@Named
@ApplicationScoped
public class AreaConverter implements Converter {

	@Inject
	CustomerForm cf;

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				List<Area> allAreas = cf.getAllAreas();
				for (Area area : allAreas) {
					if (area.getId() == Integer.parseInt(value))
						return area;
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
			return String.valueOf(((Area) object).getId());
		} else {
			return null;
		}
	}
}
