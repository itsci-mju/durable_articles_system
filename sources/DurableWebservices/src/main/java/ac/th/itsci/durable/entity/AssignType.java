package ac.th.itsci.durable.entity;

import javax.persistence.*;

@Entity
@Table(name = "assignType")
public class AssignType {
	@Id
	@Column(columnDefinition = "varchar(4)")
	private String assignType_id;
	
	@Column(name = "assignType_name", columnDefinition = "varchar(100)")
	private String assignType_name;
	
	
	public AssignType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssignType(String assignType_id, String assignType_name) {
		super();
		this.assignType_id = assignType_id;
		this.assignType_name = assignType_name;
	}

	public String getAssignType_id() {
		return assignType_id;
	}

	public void setAssignType_id(String assignType_id) {
		this.assignType_id = assignType_id;
	}

	public String getAssignType_name() {
		return assignType_name;
	}

	public void setAssignType_name(String assignType_name) {
		this.assignType_name = assignType_name;
	}

	
}
