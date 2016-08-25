package pl.chrusciel.mariusz.CDIManagedBeans;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.SelectEvent;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

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
	private Employee currentEmployee;

	@PostConstruct
	private void init() {
		this.currentEmployee = employeesBean
				.getByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
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
			this.allFaults = faultsBean.getByStatusAndEmployee(Arrays.asList(Status.PRZYPISANE.toString(),
					Status.WREALIZACJI.toString(), Status.ZAMKNIETE.toString()), currentEmployee);
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
			this.comment.setEmployee(currentEmployee);
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

	public void generateRaport() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=mycool.pdf");

		try {
			Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
			Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
			Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
			Document document = new Document();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			document.addTitle("Raport usterki: " + this.dialogFault.getId());
			Paragraph preface = new Paragraph();
			preface.add(new Paragraph(" "));
			preface.add(new Paragraph("Raport usterki: " + this.dialogFault.getId(), catFont));

			preface.add(new Paragraph(" "));
			preface.add(new Paragraph("Report wygenerowany : " + new Date(), smallBold));

			preface.add(new Paragraph(" "));
			preface.add(new Paragraph(" "));
			preface.add(new Paragraph(" "));

			document.add(preface);

			PdfPTable table = new PdfPTable(2);

			table.addCell("Id: ");
			table.addCell(String.valueOf(this.dialogFault.getId()));
			table.addCell("Typ Usterki: ");
			table.addCell(this.dialogFault.getFaultType().getType());
			table.addCell("Data zgłoszenia: ");
			table.addCell(new Date().toString());
			table.addCell("Status: ");
			table.addCell(this.dialogFault.getStatus());

			document.add(table);
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));

			table = new PdfPTable(2);

			table.addCell("Imie: ");
			table.addCell(this.dialogFault.getCustomer().getFirstName());
			table.addCell("Nazwisko: ");
			table.addCell(this.dialogFault.getCustomer().getLastName());
			table.addCell("Adres: ");
			table.addCell(this.dialogFault.getCustomer().getStreet());
			table.addCell("Miasto: ");
			table.addCell(this.dialogFault.getCustomer().getCity());
			table.addCell("Telefon: ");
			table.addCell(this.dialogFault.getCustomer().getPhoneNumber());

			document.add(table);

			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));

			Paragraph paragraph = new Paragraph("__________________________");
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			document.add(paragraph);
			Paragraph paragraph2 = new Paragraph("Podpis");
			paragraph2.setAlignment(Element.ALIGN_RIGHT);
			document.add(paragraph2);

			document.close();

			// setting the content type
			response.setContentType("application/pdf");
			// the contentlength
			response.setContentLength(baos.size());
			// write ByteArrayOutputStream to the ServletOutputStream
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		context.responseComplete();
	}

	public void onCustomerSelect(SelectEvent event) {
		Customer object = (Customer) event.getObject();
		employeeList = employeesBean.getByArea(object.getArea());
		this.employeeBtnDisabled = false;
	}

	public List<Fault> lastFaultsByStatusAndEmployee(String status) {
		if ("monter".equals(currentEmployee.getPosition()))
			return faultsBean.getByStatusAndEmployee(Arrays.asList(status), currentEmployee);
		else
			return faultsBean.getByStatus(Arrays.asList(status));
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
