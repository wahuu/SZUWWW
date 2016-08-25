package pl.chrusciel.mariusz.CDIManagedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pl.chrusciel.mariusz.ejb.InformationsBean;
import pl.chrusciel.mariusz.entities.Information;

@Named
@SessionScoped
public class DashboardForm implements Serializable {

	@Inject
	private InformationsBean informationsBean;

	private List<Information> informations;

	private String information;

	@PostConstruct
	private void init() {
		updateInformations();
	}
	
	private void updateInformations(){
		informations = informationsBean.getAll();
	}

	public void add() {
		informationsBean.add(new Information(information));
		updateInformations();
	}

	public List<Information> getInformations() {
		return informations;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public void setInformations(List<Information> informations) {
		this.informations = informations;
	}

}
