package pl.chrusciel.mariusz.CDIManagedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pl.chrusciel.mariusz.ejb.FaultTypeBean;
import pl.chrusciel.mariusz.entities.FaultType;

@Named
@SessionScoped
public class FaultTypeForm implements Serializable {

	@Inject
	FaultTypeBean faultTypeBean;

	private FaultType modifyFaultType;
	private List<FaultType> allFaultTypes;

	@PostConstruct
	public void init() {
		modifyFaultType = new FaultType();
		updateAllFaultTypes();
	}

	public void updateAllFaultTypes() {
		this.allFaultTypes = faultTypeBean.getAll();
	}

	public String goToFaultTypes() {
		this.modifyFaultType = new FaultType();
		updateAllFaultTypes();
		return "/forms/faultTypes.xhtml?faces-redirect=true";
	}

	public String add() {
		faultTypeBean.add(this.modifyFaultType);
		this.modifyFaultType = new FaultType();
		updateAllFaultTypes();
		return "/forms/faultTypes.xhtml?faces-redirect=true";
	}

	public String goToModifyFaultType(FaultType faultType) {
		this.modifyFaultType = faultType;
		return "/forms/faultTypes.xhtml?faces-redirect=true";
	}

	public String modify() {
		faultTypeBean.update(this.modifyFaultType);
		return this.goToFaultTypes();
	}

	public String delete(FaultType faultType) {
		faultTypeBean.delete(faultType);
		return this.goToFaultTypes();
	}

	public FaultType getModifyFaultType() {
		return modifyFaultType;
	}

	public void setModifyFaultType(FaultType modifyFaultType) {
		this.modifyFaultType = modifyFaultType;
	}

	public List<FaultType> getAllFaultTypes() {
		return allFaultTypes;
	}

	public void setAllFaultTypes(List<FaultType> allFaultTypes) {
		this.allFaultTypes = allFaultTypes;
	}

}
