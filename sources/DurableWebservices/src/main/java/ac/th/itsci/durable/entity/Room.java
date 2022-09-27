package ac.th.itsci.durable.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Room")
public class Room {

	@Id
	@Column(columnDefinition = "varchar(100)")
	private String Room_number;
	@Column(name = "Room_name", columnDefinition = "varchar(100)")
	private String Room_name;
	@Column(name = "Build", columnDefinition = "varchar(100)")
	private String Build;
	@Column(name = "floor", columnDefinition = "varchar(60)")
	private String floor;
	
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name = "ID_Major")
	private Major major;
	
	public Major getMajor() {
		return major;
	}

	public Room() {
		super();
	}

	public Room(String room_number, String room_name, String build, String floor) {
		super();
		Room_number = room_number;
		Room_name = room_name;
		Build = build;
		this.floor = floor;
	}
	
	public Room(String room_number, String room_name, String build, String floor, Major major) {
		super();
		Room_number = room_number;
		Room_name = room_name;
		Build = build;
		this.floor = floor;
		this.major = major;
	}

	public String getRoom_number() {
		return Room_number;
	}

	public void setRoom_number(String room_number) {
		Room_number = room_number;
	}

	public String getRoom_name() {
		return Room_name;
	}

	public void setRoom_name(String room_name) {
		Room_name = room_name;
	}

	public String getBuild() {
		return Build;
	}

	public void setBuild(String build) {
		Build = build;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

}
