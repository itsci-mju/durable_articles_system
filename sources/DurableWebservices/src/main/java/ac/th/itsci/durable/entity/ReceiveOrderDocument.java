package ac.th.itsci.durable.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "receiveOrderDocument")
@PrimaryKeyJoinColumn(name = "doc_id")
public class ReceiveOrderDocument extends Document {
	@Column(name = "receiveOrderDocument_id", columnDefinition = "varchar(50)")
	private String receiveOrderDocument_id;
	@Column(name = "receiveOrderDocument_from", columnDefinition = "varchar(100)")
	private String receiveOrderDocument_from;
	@Column(name = "invoice_number", columnDefinition = "varchar(100)")
	private String invoice_number;
	@Column(name = "receiveOrderDocument_describe", columnDefinition = "varchar(100)")
	private String receiveOrderDocument_describe;
	@Column(name = "receiveOrderDoc_makingDate")
	private Date date;
	@Column(name = "receive_order_1", columnDefinition = "varchar(20)")
	private String receive_order_1;
	@Column(name = "receive_order_2", columnDefinition = "varchar(20)")
	private String receive_order_2;

	public ReceiveOrderDocument() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReceiveOrderDocument(String doc_id, String plan_id, String plan_name, String depart_id, String depart_name,
			String fund_id, String fund_name, String money_used, String work_name, String budget, Date dates,
			String doc_title, String doc_dear, String doc_title_decribe, String doc_reason_title,
			String doc_reason_describe, String status) {
		super(doc_id, plan_id, plan_name, depart_id, depart_name, fund_id, fund_name, money_used, work_name, budget,
				dates, doc_title, doc_dear, doc_title_decribe, doc_reason_title, doc_reason_describe, status);
		// TODO Auto-generated constructor stub
	}

	public ReceiveOrderDocument(String receiveOrderDocument_id, String receiveOrderDocument_from, String invoice_number,
			String receiveOrderDocument_describe, Date date) {
		super();
		this.receiveOrderDocument_id = receiveOrderDocument_id;
		this.receiveOrderDocument_from = receiveOrderDocument_from;
		this.invoice_number = invoice_number;
		this.receiveOrderDocument_describe = receiveOrderDocument_describe;
		this.date = date;
	}

	public String getReceiveOrderDocument_id() {
		return receiveOrderDocument_id;
	}

	public void setReceiveOrderDocument_id(String receiveOrderDocument_id) {
		this.receiveOrderDocument_id = receiveOrderDocument_id;
	}

	public String getReceiveOrderDocument_from() {
		return receiveOrderDocument_from;
	}

	public void setReceiveOrderDocument_from(String receiveOrderDocument_from) {
		this.receiveOrderDocument_from = receiveOrderDocument_from;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public String getReceiveOrderDocument_describe() {
		return receiveOrderDocument_describe;
	}

	public void setReceiveOrderDocument_describe(String receiveOrderDocument_describe) {
		this.receiveOrderDocument_describe = receiveOrderDocument_describe;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getReceive_order_1() {
		return receive_order_1;
	}

	public void setReceive_order_1(String receive_order_1) {
		this.receive_order_1 = receive_order_1;
	}

	public String getReceive_order_2() {
		return receive_order_2;
	}

	public void setReceive_order_2(String receive_order_2) {
		this.receive_order_2 = receive_order_2;
	}

}
