package pl.chrusciel.mariusz.CDIManagedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import pl.chrusciel.mariusz.ejb.CommentsBean;
import pl.chrusciel.mariusz.ejb.EmployeesBean;
import pl.chrusciel.mariusz.ejb.FaultTypeBean;
import pl.chrusciel.mariusz.ejb.FaultsBean;
import pl.chrusciel.mariusz.entities.Comment;
import pl.chrusciel.mariusz.entities.Customer;
import pl.chrusciel.mariusz.entities.Employee;
import pl.chrusciel.mariusz.entities.Fault;
import pl.chrusciel.mariusz.entities.FaultType;
import pl.chrusciel.mariusz.helper.Status;

/**
 * @author Wahuu
 *
 */
@Named
@SessionScoped
public class FaultForm implements Serializable {
	@Inject
	private FaultsBean faultsBean;

	@Inject
	private FaultTypeBean faultTypeBean;

	@Inject
	private EmployeesBean employeesBean;

	@Inject
	private CommentsBean commentsBean;

	private Fault modifyFault;
	private List<Fault> allFaults;
	private List<Fault> filteredFaults;
	private List<FaultType> allFaultTypes;
	private Customer selectedCustomer;
	private Employee selectedEmployee;
	private Comment comment;

	/**
	 * Lista Employee tylko dla rejonu wybranego klienta onSelectCustomer
	 */
	private List<Employee> employeeList;
	private boolean employeeBtnDisabled = true;
	private boolean customerBtnDisabled = false;
	private List<String> statusList;
	private Fault dialogFault;

	@PostConstruct
	private void init() {
		this.modifyFault = new Fault();
		updateAllFaults();
		this.allFaultTypes = faultTypeBean.getAll();
		comment = new Comment();
		boolean userInRole = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("monter");
		if (userInRole) {
			this.statusList = new ArrayList<String>(Arrays.asList(Status.PRZYPISANE.toString(),
					Status.WREALIZACJI.toString(), Status.ZAMKNIETE.toString()));
		} else {
			this.statusList = new ArrayList<String>(Arrays.asList(Status.ZGLOSZONE.toString(),
					Status.PRZYPISANE.toString(), Status.WREALIZACJI.toString(), Status.ZAMKNIETE.toString()));
		}
	}

	public void updateAllFaults() {
		boolean userInRole = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("monter");
		if (userInRole) {
			this.allFaults = faultsBean.getByStatus(Arrays.asList(Status.PRZYPISANE.toString(),
					Status.WREALIZACJI.toString(), Status.ZAMKNIETE.toString()));
		} else {
			this.allFaults = faultsBean.getAll();
		}
	}

	public String goToNew() {
		modifyFault = new Fault();
		comment = new Comment();
		updateAllFaults();
		this.employeeBtnDisabled = true;
		this.customerBtnDisabled = false;
		this.selectedCustomer = null;
		this.selectedEmployee = null;
		return "/forms/faultsModify.xhtml?faces-redirect=true";
	}

	public String add() {
		validateComment();

		this.modifyFault.setFilingDate(Calendar.getInstance().getTime());
		if (selectedCustomer != null)
			this.modifyFault.setCustomer(selectedCustomer);
		if (selectedEmployee != null)
			this.modifyFault.setEmployee(selectedEmployee);
		if (this.modifyFault.getStatus() == null)
			this.modifyFault.setStatus(Status.ZGLOSZONE.toString());
		faultsBean.add(this.modifyFault);
		this.modifyFault = new Fault();
		updateAllFaults();
		return "/forms/faults.xhtml?faces-redirect=true";
	}

	private void validateComment() {
		if (this.comment.getComment() != null && !"".equals(this.comment.getComment().trim())) {
			this.comment.setEmployee(
					employeesBean.getByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()));
			this.comment = commentsBean.add(comment);
			if (this.modifyFault.getComments() != null) {
				this.modifyFault.getComments().add(this.comment);
			} else {
				this.modifyFault.setComments(new ArrayList<Comment>(Arrays.asList(this.comment)));
			}
		}
	}

	public String goToModify(Fault fault) {
		this.modifyFault = fault;
		comment = new Comment();
		this.selectedCustomer = fault.getCustomer();
		this.selectedEmployee = fault.getEmployee();
		this.customerBtnDisabled = true;
		this.employeeBtnDisabled = false;
		employeeList = employeesBean.getByArea(this.modifyFault.getCustomer().getArea());
		return "/forms/faultsModify.xhtml?faces-redirect=true";
	}

	public String modify() {
		validateComment();

		if (selectedCustomer != null)
			this.modifyFault.setCustomer(selectedCustomer);
		if (selectedEmployee != null)
			this.modifyFault.setEmployee(selectedEmployee);
		faultsBean.update(modifyFault);
		updateAllFaults();
		return "/forms/faults.xhtml?faces-redirect=true";
	}

	public String delete(Fault fault) {
		faultsBean.delete(fault);
		updateAllFaults();
		return "/forms/faults.xhtml?faces-redirect=true";
	}

	public void onCustomerSelect(SelectEvent event) {
		Customer object = (Customer) event.getObject();
		employeeList = employeesBean.getByArea(object.getArea());
		this.employeeBtnDisabled = false;
	}

	public Fault getModifyFault() {
		return modifyFault;
	}

	public void setModifyFault(Fault modifyFault) {
		this.modifyFault = modifyFault;
	}

	public List<Fault> getAllFaults() {
		return allFaults;
	}

	public void setAllFaults(List<Fault> allFaults) {
		this.allFaults = allFaults;
	}

	public List<Fault> getFilteredFaults() {
		return filteredFaults;
	}

	public void setFilteredFaults(List<Fault> filteredFaults) {
		this.filteredFaults = filteredFaults;
	}

	public List<FaultType> getAllFaultTypes() {
		return allFaultTypes;
	}

	public void setAllFaultTypes(List<FaultType> allFaultTypes) {
		this.allFaultTypes = allFaultTypes;
	}

	public Customer getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(Customer selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public boolean isEmployeeBtnDisabled() {
		return employeeBtnDisabled;
	}

	public void setEmployeeBtnDisabled(boolean employeeListDisabled) {
		this.employeeBtnDisabled = employeeListDisabled;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

	public boolean isCustomerBtnDisabled() {
		return customerBtnDisabled;
	}

	public void setCustomerBtnDisabled(boolean customerBtnDisabled) {
		this.customerBtnDisabled = customerBtnDisabled;
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public Fault getDialogFault() {
		return dialogFault;
	}

	public void setDialogFault(Fault dialogFault) {
		this.dialogFault = dialogFault;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

}
