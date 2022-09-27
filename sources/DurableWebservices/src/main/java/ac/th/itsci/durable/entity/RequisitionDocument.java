package ac.th.itsci.durable.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "requisitionDocument")
public class RequisitionDocument {
	@Id
	@Column(columnDefinition = "varchar(10)")
	private String requisition_id;
	@Column(name = "requisition_date")
	private Date requisition_date;
	@Column(name="requisition_budget_year")
	private int requisition_budget_year;
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "pk.requisition")
	private List<RequisitionItem> requisitionItem = new ArrayList<>();

	public RequisitionDocument() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequisitionDocument(String requisition_id, Date requisition_date) {
		super();
		this.requisition_id = requisition_id;
		this.requisition_date = requisition_date;
	}

	public String getRequisition_id() {
		return requisition_id;
	}

	public void setRequisition_id(String requisition_id) {
		this.requisition_id = requisition_id;
	}

	public Date getRequisition_date() {
		return requisition_date;
	}

	public void setRequisition_date(Date requisition_date) {
		this.requisition_date = requisition_date;
	}

	public int getRequisition_budget_year() {
		return requisition_budget_year;
	}

	public void setRequisition_budget_year(int requisition_budget_year) {
		this.requisition_budget_year = requisition_budget_year;
	}

	public List<RequisitionItem> getRequisitionItem() {
		return requisitionItem;
	}

	public void setRequisitionItem(List<RequisitionItem> requisitionItem) {
		this.requisitionItem = requisitionItem;
	}
	
	
	

}
