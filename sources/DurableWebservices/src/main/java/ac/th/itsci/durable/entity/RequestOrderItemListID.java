package ac.th.itsci.durable.entity;

import java.io.*;
import javax.persistence.*;

@Embeddable
public class RequestOrderItemListID implements Serializable {
	
	@Column(name="requestOrderItemList_id", columnDefinition = "varchar(20)")
	private String requestOrderItemList_id;
	@ManyToOne
	private Document document;
	@ManyToOne
	private Item item;
	
	public RequestOrderItemListID() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestOrderItemListID(Document document, Item item) {
		super();
		this.document = document;
		this.item = item;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getRequestOrderItemList_id() {
		return requestOrderItemList_id;
	}

	public void setRequestOrderItemList_id(String requestOrderItemList_id) {
		this.requestOrderItemList_id = requestOrderItemList_id;
	}
	
	
}
