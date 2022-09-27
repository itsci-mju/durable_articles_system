package ac.th.itsci.durable.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "requisitionItem")
public class RequisitionItem {
	
	@EmbeddedId
	private RequisitionItemID pk = new RequisitionItemID();
	@Column(name = "requisition_total")
	private int requisition_total;
	@Column(name = "requisition_total_balance")
	private int requisition_total_balance;
	@Column(name = "total_price_purchase")
	private Double total_price_purchase;
	@Column(name = "total_price_balance")
	private Double total_price_balance;
	@Column(name = "requisition_note", columnDefinition = "varchar(50)")
	private String requisition_note;
	@Column(name="last_balance")
	private int last_balance;

	public RequisitionItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequisitionItem(RequisitionItemID pk, int requisition_total, int requisition_total_balance,
			Double total_price_purchase, Double total_price_balance, String requisition_note) {
		super();
		this.pk = pk;
		this.requisition_total = requisition_total;
		this.requisition_total_balance = requisition_total_balance;
		this.total_price_purchase = total_price_purchase;
		this.total_price_balance = total_price_balance;
		this.requisition_note = requisition_note;
	}

	public RequisitionItemID getPk() {
		return pk;
	}

	public void setPk(RequisitionItemID pk) {
		this.pk = pk;
	}

	public int getRequisition_total() {
		return requisition_total;
	}

	public void setRequisition_total(int requisition_total) {
		this.requisition_total = requisition_total;
	}

	public int getRequisition_total_balance() {
		return requisition_total_balance;
	}

	public void setRequisition_total_balance(int requisition_total_balance) {
		this.requisition_total_balance = requisition_total_balance;
	}

	public Double getTotal_price_purchase() {
		return total_price_purchase;
	}

	public void setTotal_price_purchase(Double total_price_purchase) {
		this.total_price_purchase = total_price_purchase;
	}

	public Double getTotal_price_balance() {
		return total_price_balance;
	}

	public void setTotal_price_balance(Double total_price_balance) {
		this.total_price_balance = total_price_balance;
	}

	public String getRequisition_note() {
		return requisition_note;
	}

	public void setRequisition_note(String requisition_note) {
		this.requisition_note = requisition_note;
	}

	public int getLast_balance() {
		return last_balance;
	}

	public void setLast_balance(int last_balance) {
		this.last_balance = last_balance;
	}

}
