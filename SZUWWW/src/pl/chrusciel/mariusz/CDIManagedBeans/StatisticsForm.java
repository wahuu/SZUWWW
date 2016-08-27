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

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
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
	private Date dateFrom2;
	private Date dateTo2;

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

	public void createAreaChartFaultsCount() {
		faultsBean.countFaults(dateFrom2, dateTo2);
		areaModel = new LineChartModel();

		LineChartSeries phones = new LineChartSeries();
		phones.setFill(true);
		phones.setLabel("Telefony");
		phones.set("01", 10);
		phones.set("02", 12);
		phones.set("03", 13);

		LineChartSeries internet = new LineChartSeries();
		internet.setFill(true);
		internet.setLabel("Internet");
		internet.set("01", 20);
		internet.set("02", 22);
		internet.set("03", 23);

		areaModel.addSeries(phones);
		areaModel.addSeries(internet);
		areaModel.setTitle("Title");
		areaModel.setLegendPosition("ne");
		areaModel.setStacked(true);
		areaModel.setShowPointLabels(true);

		Axis categoryAxis = new CategoryAxis("Days");
		areaModel.getAxes().put(AxisType.X, categoryAxis);

		Axis yAxis = areaModel.getAxis(AxisType.Y);
		yAxis.setLabel("Zgłoszenia");
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

	public Date getDateFrom2() {
		return dateFrom2;
	}

	public void setDateFrom2(Date dateFrom2) {
		this.dateFrom2 = dateFrom2;
	}

	public Date getDateTo2() {
		return dateTo2;
	}

	public void setDateTo2(Date dateTo2) {
		this.dateTo2 = dateTo2;
	}

}
