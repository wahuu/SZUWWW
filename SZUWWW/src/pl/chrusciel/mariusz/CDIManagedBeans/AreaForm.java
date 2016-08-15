package pl.chrusciel.mariusz.CDIManagedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pl.chrusciel.mariusz.ejb.AreaBean;
import pl.chrusciel.mariusz.entities.Area;

@Named
@SessionScoped
public class AreaForm implements Serializable {

	@Inject
	AreaBean areaBean;

	private Area modifyArea;
	private List<Area> allAreas;

	@PostConstruct
	private void init() {
		modifyArea = new Area();
		updateAllAreas();
	}

	public void updateAllAreas() {
		this.allAreas = areaBean.getAll();
	}

	public String goToNewArea() {
		modifyArea = new Area();
		updateAllAreas();
		return "/forms/areasModify.xhtml?faces-redirect=true";
	}

	public String addArea() {
		areaBean.add(modifyArea);
		modifyArea = new Area();
		updateAllAreas();
		return "/forms/areas.xhtml?faces-redirect=true";
	}

	public String goToModify(Area area) {
		this.modifyArea = area;
		return "/forms/areasModify.xhtml?faces-redirect=true";
	}

	public String modify() {
		areaBean.update(modifyArea);
		updateAllAreas();
		return "/forms/areas.xhtml?faces-redirect=true";
	}

	public String deleteArea(Area area) {
		areaBean.delete(area);
		updateAllAreas();
		return "/forms/areas.xhtml?faces-redirect=true";
	}

	public Area getModifyArea() {
		return modifyArea;
	}

	public void setModifyArea(Area modifyArea) {
		this.modifyArea = modifyArea;
	}

	public List<Area> getAllAreas() {
		return allAreas;
	}

	public void setAllAreas(List<Area> allAreas) {
		this.allAreas = allAreas;
	}

}
