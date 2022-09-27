package ac.th.itsci.durable.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "purchaseOrderDocument")
@PrimaryKeyJoinColumn(name = "doc_id")
public class PurchaseOrderDocument extends Document {
	@Column(name = "purchaseOrderDocument_id", columnDefinition = "varchar(50)")
	private String purchaseOrderDocument_id;
	@Column(name = "purchaseOrder_type", columnDefinition = "varchar(100)")
	private String purchaseOrder_type;
	@Column(name = "purchaseOrderDoc_describe", columnDefinition = "varchar(255)")
	private String purchaseOrderDoc_describe;
	@Column(name = "making_date")
	private Date date;
	// all_signature
	@Column(name = "requisition_person", columnDefinition = "varchar(20)")
	private String requisition_person;
	@Column(name = "faculty_dean", columnDefinition = "varchar(20)")
	private String faculty_dean;
	@Column(name = "purchase_order_1", columnDefinition = "varchar(20)")
	private String purchase_order_1;
	@Column(name="purchase_order_3", columnDefinition = "varchar(20)")
	private String purchase_order_3;
	@Column(name="pruchase_order_4", columnDefinition = "varchar(20)")
	private String purchase_order_4;
	@Column(name="purchase_order_5", columnDefinition = "varchar(20)")
	private String purchase_order_5;

	public PurchaseOrderDocument() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PurchaseOrderDocument(String doc_id, String plan_id, String plan_name, String depart_id, String depart_name,
			String fund_id, String fund_name, String money_used, String work_name, String budget, Date dates,
			String doc_title, String doc_dear, String doc_title_decribe, String doc_reason_title,
			String doc_reason_describe, String status) {
		super(doc_id, plan_id, plan_name, depart_id, depart_name, fund_id, fund_name, money_used, work_name, budget,
				dates, doc_title, doc_dear, doc_title_decribe, doc_reason_title, doc_reason_describe, status);
		// TODO Auto-generated constructor stub
	}

	public PurchaseOrderDocument(String purchaseOrderDocument_id, String purchaseOrder_type,
			String purchaseOrderDoc_describe, Date date, String requisition_person, String faculty_dean) {
		super();
		this.purchaseOrderDocument_id = purchaseOrderDocument_id;
		this.purchaseOrder_type = purchaseOrder_type;
		this.purchaseOrderDoc_describe = purchaseOrderDoc_describe;
		this.date = date;
		this.requisition_person = requisition_person;
		this.faculty_dean = faculty_dean;
	}

	public String getPurchaseOrderDocument_id() {
		return purchaseOrderDocument_id;
	}

	public void setPurchaseOrderDocument_id(String purchaseOrderDocument_id) {
		this.purchaseOrderDocument_id = purchaseOrderDocument_id;
	}

	public String getPurchaseOrder_type() {
		return purchaseOrder_type;
	}

	public void setPurchaseOrder_type(String purchaseOrder_type) {
		this.purchaseOrder_type = purchaseOrder_type;
	}

	public String getPurchaseOrderDoc_describe() {
		return purchaseOrderDoc_describe;
	}

	public void setPurchaseOrderDoc_describe(String purchaseOrderDoc_describe) {
		this.purchaseOrderDoc_describe = purchaseOrderDoc_describe;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRequisition_person() {
		return requisition_person;
	}

	public void setRequisition_person(String requisition_person) {
		this.requisition_person = requisition_person;
	}

	public String getFaculty_dean() {
		return faculty_dean;
	}

	public void setFaculty_dean(String faculty_dean) {
		this.faculty_dean = faculty_dean;
	}

	public String getPurchase_order_1() {
		return purchase_order_1;
	}

	public void setPurchase_order_1(String purchase_order_1) {
		this.purchase_order_1 = purchase_order_1;
	}

	public String getPurchase_order_3() {
		return purchase_order_3;
	}

	public void setPurchase_order_3(String purchase_order_3) {
		this.purchase_order_3 = purchase_order_3;
	}

	public String getPurchase_order_4() {
		return purchase_order_4;
	}

	public void setPurchase_order_4(String purchase_order_4) {
		this.purchase_order_4 = purchase_order_4;
	}

	public String getPurchase_order_5() {
		return purchase_order_5;
	}

	public void setPurchase_order_5(String purchase_order_5) {
		this.purchase_order_5 = purchase_order_5;
	}
	
	

}
