package ac.th.itsci.durable.entity;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "BillOfLading")
@PrimaryKeyJoinColumn(name = "doc_id")
public class BillOfLading extends Document {
	
	@Column(name="BillOfLading_id", columnDefinition = "varchar(100)")
	private String billOfLading_id;
	@Column(name="representative", columnDefinition = "varchar(100)")
	private String representative;
	@Column(name="receive_person", columnDefinition = "varchar(100)")
	private String receive_person;
	@Column(name="bill_of_lading_1", columnDefinition = "varchar(20)")
	private String bill_of_lading_1;
	@Column(name="bill_of_lading_2", columnDefinition = "varchar(20)")
	private String bill_of_lading_2;
	@Column(name="bill_of_lading_4", columnDefinition = "varchar(20)")
	private String bill_of_lading_4;
	@Column(name="bill_of_lading_5", columnDefinition = "varchar(20)")
	private String bill_of_lading_5;

	public BillOfLading() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BillOfLading(String doc_id, String plan_id, String plan_name, String depart_id, String depart_name,
			String fund_id, String fund_name, String money_used, String work_name, String budget, Date dates,
			String doc_title, String doc_dear, String doc_title_decribe, String doc_reason_title,
			String doc_reason_describe, String status) {
		super(doc_id, plan_id, plan_name, depart_id, depart_name, fund_id, fund_name, money_used, work_name, budget, dates,
				doc_title, doc_dear, doc_title_decribe, doc_reason_title, doc_reason_describe, status);
		// TODO Auto-generated constructor stub
	}

	public BillOfLading(String billOfLading_id, String representative, String receive_person) {
		super();
		this.billOfLading_id = billOfLading_id;
		this.representative = representative;
		this.receive_person = receive_person;
	}

	public String getBillOfLading_id() {
		return billOfLading_id;
	}

	public void setBillOfLading_id(String billOfLading_id) {
		this.billOfLading_id = billOfLading_id;
	}

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	public String getReceive_person() {
		return receive_person;
	}

	public void setReceive_person(String receive_person) {
		this.receive_person = receive_person;
	}

	public String getBill_of_lading_1() {
		return bill_of_lading_1;
	}

	public void setBill_of_lading_1(String bill_of_lading_1) {
		this.bill_of_lading_1 = bill_of_lading_1;
	}

	public String getBill_of_lading_2() {
		return bill_of_lading_2;
	}

	public void setBill_of_lading_2(String bill_of_lading_2) {
		this.bill_of_lading_2 = bill_of_lading_2;
	}

	public String getBill_of_lading_4() {
		return bill_of_lading_4;
	}

	public void setBill_of_lading_4(String bill_of_lading_4) {
		this.bill_of_lading_4 = bill_of_lading_4;
	}

	public String getBill_of_lading_5() {
		return bill_of_lading_5;
	}

	public void setBill_of_lading_5(String bill_of_lading_5) {
		this.bill_of_lading_5 = bill_of_lading_5;
	}

	
	
	
}
