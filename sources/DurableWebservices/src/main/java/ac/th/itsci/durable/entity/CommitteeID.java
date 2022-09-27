package ac.th.itsci.durable.entity;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class CommitteeID implements Serializable {
	
	@ManyToOne
	private Personnel personnel;
	@ManyToOne
	private Document document;
	

	public CommitteeID() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommitteeID(Personnel personnel, Document document) {
		super();
		this.personnel = personnel;
		this.document = document;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
