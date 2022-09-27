package ac.th.itsci.durable.entity;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "personnel")
public class Personnel {
	@Id
	@Column(columnDefinition = "varchar(50)")
	private String personnel_id;

	@Column(name = "personnel_prefix", columnDefinition = "varchar(50)")
	private String personnel_prefix;

	@Column(name = "personnel_firstName", columnDefinition = "varchar(100)")
	private String personnel_firstName;

	@Column(name = "personnel_lastName", columnDefinition = "varchar(100)")
	private String personnel_lastName;

	@Column(name = "personnel_position", columnDefinition = "varchar(100)")
	private String personnel_position;
	
	@Column(name = "personnel_tier", columnDefinition = "varchar(100)")
	private String personnel_tier;
	
	@Column(name="personnel_executive_position", columnDefinition = "varchar(100)")
	private String personnel_executive_position;

	@ManyToOne
	@JoinColumn(name = "position_id")
	private Position position;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "committee.personnel")
	private List<Committee> committee = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name="ID_Major")
	private Major major;

	public Personnel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Personnel(String personnel_id, String personnel_prefix, String personnel_firstName,
			String personnel_lastName, String personnel_position) {
		super();
		this.personnel_id = personnel_id;
		this.personnel_prefix = personnel_prefix;
		this.personnel_firstName = personnel_firstName;
		this.personnel_lastName = personnel_lastName;
		this.personnel_position = personnel_position;
	}

	public String getPersonnel_id() {
		return personnel_id;
	}

	public void setPersonnel_id(String personnel_id) {
		this.personnel_id = personnel_id;
	}

	public String getPersonnel_prefix() {
		return personnel_prefix;
	}

	public void setPersonnel_prefix(String personnel_prefix) {
		this.personnel_prefix = personnel_prefix;
	}

	public String getPersonnel_firstName() {
		return personnel_firstName;
	}

	public void setPersonnel_firstName(String personnel_firstName) {
		this.personnel_firstName = personnel_firstName;
	}

	public String getPersonnel_lastName() {
		return personnel_lastName;
	}

	public void setPersonnel_lastName(String personnel_lastName) {
		this.personnel_lastName = personnel_lastName;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getPersonnel_position() {
		return personnel_position;
	}

	public void setPersonnel_position(String personnel_position) {
		this.personnel_position = personnel_position;
	}

	public List<Committee> getCommittee() {
		return committee;
	}

	public void setCommittee(List<Committee> committee) {
		this.committee = committee;
	}
	
	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public String getPersonnel_tier() {
		return personnel_tier;
	}

	public void setPersonnel_tier(String personnel_tier) {
		this.personnel_tier = personnel_tier;
	}

	public String getPersonnel_executive_position() {
		return personnel_executive_position;
	}

	public void setPersonnel_executive_position(String personnel_executive_position) {
		this.personnel_executive_position = personnel_executive_position;
	}
	
	

}
