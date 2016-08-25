package pl.chrusciel.mariusz.CDIManagedBeans;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

import pl.chrusciel.mariusz.ejb.FaultsBean;

@Named
@SessionScoped
public class StatisticsForm implements Serializable {

	@Inject
	private FaultsBean faultsBean;

	private PieChartModel pieModel1;
	private LineChartModel areaModel;
	private Date dateFrom;
	private Date dateTo;

	@PostConstruct
	private void init() {
		// createPieModel1();
	}

	public void createPieCountForFaultType() {
		List<HashMap<String, Long>> countFaultsForFaultTypes = faultsBean.countFaultsForFaultTypes(dateFrom, dateTo);
		pieModel1 = new PieChartModel();

		for (HashMap<String, Long> hashMap : countFaultsForFaultTypes) {
			for (Entry<String, Long> entry : hashMap.entrySet()) {
				pieModel1.set(entry.getKey(), entry.getValue());
			}
		}
		pieModel1.setTitle("Statystyka typów usterek");
		pieModel1.setLegendPosition("e");
		pieModel1.setShowDataLabels(true);
	}
	
	public void createPieFaultsCount(){
		// Area chart
	}

	public PieChartModel getPieModel1() {
		return pieModel1;
	}

	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public LineChartModel getAreaModel() {
		return areaModel;
	}

	public void setAreaModel(LineChartModel areaModel) {
		this.areaModel = areaModel;
	}

}
