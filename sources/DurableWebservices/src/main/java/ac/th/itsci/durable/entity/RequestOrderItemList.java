package ac.th.itsci.durable.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "requestOrderItemList")
public class RequestOrderItemList {

	@EmbeddedId
	private RequestOrderItemListID id = new RequestOrderItemListID();
	@Column(name = "item_price")
	private Double item_price;
	@Column(name = "total_price")
	private Double total_price;
	@Column(name = "amount")
	private int amount;
	@Column(name = "prescription")
	private Date date;
	@Column(name = "note", columnDefinition = "varchar(255)")
	private String note;
	@Column(name = "amount_in_invoice")
	private Integer amount_in_invoice;
	@Column(name = "amount_received")
	private Integer amount_received;
	@Column(name = "amount_balance")
	private Integer amount_balance;
	@Column(name = "budget_year")
	private int budget_year;
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "pk.requestOrderItemList")
	private List<RequisitionItem> requisitionItem = new ArrayList<>();

	public RequestOrderItemList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestOrderItemList(Double total_price, int amount) {
		super();
		this.total_price = total_price;
		this.amount = amount;
	}

	public RequestOrderItemListID getId() {
		return id;
	}

	public void setId(RequestOrderItemListID id) {
		this.id = id;
	}

	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getAmount_in_invoice() {
		return amount_in_invoice;
	}

	public void setAmount_in_invoice(Integer amount_in_invoice) {
		this.amount_in_invoice = amount_in_invoice;
	}

	public int getAmount_received() {
		return amount_received;
	}

	public void setAmount_received(Integer amount_received) {
		this.amount_received = amount_received;
	}

	public Double getItem_price() {
		return item_price;
	}

	public void setItem_price(Double item_price) {
		this.item_price = item_price;
	}

	public Integer getAmount_balance() {
		return amount_balance;
	}

	public void setAmount_balance(Integer amount_balance) {
		this.amount_balance = amount_balance;
	}

	public int getBudget_year() {
		return budget_year;
	}

	public void setBudget_year(int budget_year) {
		this.budget_year = budget_year;
	}

	public List<RequisitionItem> getRequisitionItem() {
		return requisitionItem;
	}

	public void setRequisitionItem(List<RequisitionItem> requisitionItem) {
		this.requisitionItem = requisitionItem;
	}

}
