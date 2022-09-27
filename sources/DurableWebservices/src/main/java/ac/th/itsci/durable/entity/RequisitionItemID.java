package ac.th.itsci.durable.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class RequisitionItemID implements Serializable {

//	@ManyToOne
//	private Item item;
	
	@ManyToOne
	private RequestOrderItemList requestOrderItemList;
	@ManyToOne
	private Personnel personnel;
	@ManyToOne
	private RequisitionDocument requisition;

	public RequisitionItemID() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestOrderItemList getRequestOrderItemList() {
		return requestOrderItemList;
	}

	public void setRequestOrderItemList(RequestOrderItemList requestOrderItemList) {
		this.requestOrderItemList = requestOrderItemList;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public RequisitionDocument getRequisition() {
		return requisition;
	}

	public void setRequisition(RequisitionDocument requisition) {
		this.requisition = requisition;
	}

//	public RequisitionItemID( Item item, Personnel personnel) {
//		super();
//		this.item = item;
//		this.personnel = personnel;
//	}

//	public Document getDocument() {
//		return document;
//	}
//
//	public void setDocument(Document document) {
//		this.document = document;
//	}

//	public Item getItem() {
//		return item;
//	}
//
//	public void setItem(Item item) {
//		this.item = item;
//	}
//
//	public Personnel getPersonnel() {
//		return personnel;
//	}
//
//	public void setPersonnel(Personnel personnel) {
//		this.personnel = personnel;
//	}
//
//	public RequisitionDocument getRequisition() {
//		return requisition;
//	}
//
//	public void setRequisition(RequisitionDocument requisition) {
//		this.requisition = requisition;
//	}

	
	
}
