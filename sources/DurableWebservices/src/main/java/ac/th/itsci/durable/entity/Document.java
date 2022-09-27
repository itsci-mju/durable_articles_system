package ac.th.itsci.durable.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "document")
@Inheritance(strategy = InheritanceType.JOINED)
public class Document {
	@Id
	@Column(name = "doc_id", columnDefinition = "varchar(50)")
	private String doc_id;
	@Column(name = "plan_id", columnDefinition = "varchar(10)")
	private String plan_id;
	@Column(name = "plan_name", columnDefinition = "varchar(255)")
	private String plan_name;
	@Column(name = "depart_id", columnDefinition = "varchar(10)")
	private String depart_id;
	@Column(name = "depart_name", columnDefinition = "varchar(255)")
	private String depart_name;
	@Column(name = "fund_id", columnDefinition = "varchar(10)")
	private String fund_id;
	@Column(name = "fund_name", columnDefinition = "varchar(255)")
	private String fund_name;
	@Column(name = "money_used", columnDefinition = "varchar(100)")
	private String money_used;
	@Column(name = "work_name", columnDefinition = "varchar(100)")
	private String work_name;
	@Column(name = "budget", columnDefinition = "varchar(100)")
	private String budget;
	@Column(name = "doc_date")
	private Date doc_date;
	@Column(name = "doc_title", columnDefinition = "varchar(255)")
	private String doc_title;
	@Column(name = "doc_dear", columnDefinition = "varchar(255)")
	private String doc_dear;
	@Column(name = "doc_title_describe", columnDefinition = "varchar(255)")
	private String doc_title_decribe;
	@Column(name = "doc_reason_title", columnDefinition = "varchar(255)")
	private String doc_reason_title;
	@Column(name = "doc_reason_describe", columnDefinition = "varchar(255)")
	private String doc_reason_describe;
	@Column(name = "doc_status", columnDefinition = "varchar(255)")
	private String status;
	@Column(name = "price_txt", columnDefinition = "varchar(255)")
	private String price_txt;
	@Column(name = "total_price")
	private Double total_price;
	@Column(name = "price_with_out_tax")
	private Double price_with_out_tax;
	@Column(name = "tax")
	private Double tax;
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "id.document")
	private List<RequestOrderItemList> requisitionItemLists = new ArrayList<>();
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "committee.document")
	private List<Committee> committee = new ArrayList<>();
	// all_signature
	@Column(name = "request_order_person", columnDefinition = "varchar(20)")
	private String request_order_person;
	@Column(name = "accounting_officer", columnDefinition = "varchar(20)")
	private String accounting_officer;
	@Column(name = "chief_of_procurement", columnDefinition = "varchar(20)")
	private String chief_of_procurement;
	@Column(name = "secretary", columnDefinition = "varchar(20)")
	private String secretary;
	
	@Column(name="store_name", columnDefinition = "varchar(30)")
	private String store_name;
	@Column(name="vat_check", columnDefinition = "varchar(10)")
	private String vat_check;

	public Document() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Document(String doc_id, String plan_id, String plan_name, String depart_id, String depart_name,
			String fund_id, String fund_name, String money_used, String work_name, String budget, Date doc_date,
			String doc_title, String doc_dear, String doc_title_decribe, String doc_reason_title,
			String doc_reason_describe, String status) {
		super();
		this.doc_id = doc_id;
		this.plan_id = plan_id;
		this.plan_name = plan_name;
		this.depart_id = depart_id;
		this.depart_name = depart_name;
		this.fund_id = fund_id;
		this.fund_name = fund_name;
		this.money_used = money_used;
		this.work_name = work_name;
		this.budget = budget;
		this.doc_date = doc_date;
		this.doc_title = doc_title;
		this.doc_dear = doc_dear;
		this.doc_title_decribe = doc_title_decribe;
		this.doc_reason_title = doc_reason_title;
		this.doc_reason_describe = doc_reason_describe;
		this.status = status;
	}

	public String getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(String doc_id) {
		this.doc_id = doc_id;
	}

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public String getPlan_name() {
		return plan_name;
	}

	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}

	public String getDepart_id() {
		return depart_id;
	}

	public void setDepart_id(String depart_id) {
		this.depart_id = depart_id;
	}

	public String getDepart_name() {
		return depart_name;
	}

	public void setDepart_name(String depart_name) {
		this.depart_name = depart_name;
	}

	public String getFund_id() {
		return fund_id;
	}

	public void setFund_id(String fund_id) {
		this.fund_id = fund_id;
	}

	public String getFund_name() {
		return fund_name;
	}

	public void setFund_name(String fund_name) {
		this.fund_name = fund_name;
	}

	public String getMoney_used() {
		return money_used;
	}

	public void setMoney_used(String money_used) {
		this.money_used = money_used;
	}

	public String getWork_name() {
		return work_name;
	}

	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public Date getDoc_date() {
		return doc_date;
	}

	public void setDoc_date(Date doc_date) {
		this.doc_date = doc_date;
	}

	public String getDoc_title() {
		return doc_title;
	}

	public void setDoc_title(String doc_title) {
		this.doc_title = doc_title;
	}

	public String getDoc_dear() {
		return doc_dear;
	}

	public void setDoc_dear(String doc_dear) {
		this.doc_dear = doc_dear;
	}

	public String getDoc_title_decribe() {
		return doc_title_decribe;
	}

	public void setDoc_title_decribe(String doc_title_decribe) {
		this.doc_title_decribe = doc_title_decribe;
	}

	public String getDoc_reason_title() {
		return doc_reason_title;
	}

	public void setDoc_reason_title(String doc_reason_title) {
		this.doc_reason_title = doc_reason_title;
	}

	public String getDoc_reason_describe() {
		return doc_reason_describe;
	}

	public void setDoc_reason_describe(String doc_reason_describe) {
		this.doc_reason_describe = doc_reason_describe;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrice_txt() {
		return price_txt;
	}

	public void setPrice_txt(String price_txt) {
		this.price_txt = price_txt;
	}

	public List<RequestOrderItemList> getRequisitionItemLists() {
		return requisitionItemLists;
	}

	public void setRequisitionItemLists(List<RequestOrderItemList> requisitionItemLists) {
		this.requisitionItemLists = requisitionItemLists;
	}

	public List<Committee> getCommittee() {
		return committee;
	}

	public void setCommittee(List<Committee> committee) {
		this.committee = committee;
	}

	public String getRequest_order_person() {
		return request_order_person;
	}

	public void setRequest_order_person(String request_order_person) {
		this.request_order_person = request_order_person;
	}

	public String getAccounting_officer() {
		return accounting_officer;
	}

	public void setAccounting_officer(String accounting_officer) {
		this.accounting_officer = accounting_officer;
	}

	public String getChief_of_procurement() {
		return chief_of_procurement;
	}

	public void setChief_of_procurement(String chief_of_procurement) {
		this.chief_of_procurement = chief_of_procurement;
	}

	public String getSecretary() {
		return secretary;
	}

	public void setSecretary(String secretary) {
		this.secretary = secretary;
	}

	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}

	public Double getPrice_with_out_tax() {
		return price_with_out_tax;
	}

	public void setPrice_with_out_tax(Double price_with_out_tax) {
		this.price_with_out_tax = price_with_out_tax;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getVat_check() {
		return vat_check;
	}

	public void setVat_check(String vat_check) {
		this.vat_check = vat_check;
	}
	
	

}
