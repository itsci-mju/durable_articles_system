package ac.th.itsci.durable.entity;

import javax.persistence.*;
import java.util.*;
@Entity
@Table(name = "item")
public class Item {
	@Id
	@Column(columnDefinition = "varchar(50)")
	private String item_id;
	@Column(name = "item_name", columnDefinition = "varchar(100)")
	private String item_name;
	@Column(name = "item_unit", columnDefinition = "varchar(100)")
	private String item_unit;
	@Column(name = "item_price")
	private Double item_price;
	@Column(name = "item_note", columnDefinition = "varchar(255)")
	private String item_note;
	@Column(name="item_category", columnDefinition="varchar(100)")
	private String item_category;
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "id.item")
	private List<RequestOrderItemList> requisitionItemLists = new ArrayList<>();
	
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Item(String item_id, String item_name, String item_unit, Double item_price, String item_note, String item_category) {
		super();
		this.item_id = item_id;
		this.item_name = item_name;
		this.item_unit = item_unit;
		this.item_price = item_price;
		this.item_note = item_note;
		this.item_category = item_category;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_unit() {
		return item_unit;
	}

	public void setItem_unit(String item_unit) {
		this.item_unit = item_unit;
	}

	public Double getItem_price() {
		return item_price;
	}

	public void setItem_price(Double item_price) {
		this.item_price = item_price;
	}

	public List<RequestOrderItemList> getRequisitionItemLists() {
		return requisitionItemLists;
	}

	public void setRequisitionItemLists(List<RequestOrderItemList> requisitionItemLists) {
		this.requisitionItemLists = requisitionItemLists;
	}

	public String getItem_note() {
		return item_note;
	}

	public void setItem_note(String item_note) {
		this.item_note = item_note;
	}

	public String getItem_category() {
		return item_category;
	}

	public void setItem_category(String item_category) {
		this.item_category = item_category;
	}
	
	
	
	
	
}
