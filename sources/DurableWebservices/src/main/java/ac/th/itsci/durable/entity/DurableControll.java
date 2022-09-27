package ac.th.itsci.durable.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import java.util.*;

@Entity
@Table(name = "durableControll")
public class DurableControll extends Durable{
	
	@Column(name = "durable_type", columnDefinition = "varchar(255)")
	private String durable_type;
	@Column(name = "durable_serial_number", columnDefinition = "varchar(255)")
	private String durable_serial_number;
	@Column(name = "durable_optain_type", columnDefinition = "varchar(255)")
	private String durable_optain_type;
	@Column(name = "durable_petition_number", columnDefinition = "varchar(255)")
	private String durable_petition_number;
	@Column(name = "durable_money_type", columnDefinition = "varchar(255)")
	private String durable_money_type;
	@Column(name = "durable_life_time")
	private int durable_life_time;
	@Column(name = "budget_year", columnDefinition = "varchar(5)")
	private String budget_year;
	@Column(name = "depreciation_rate")
	private Double depreciation_rate;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	public DurableControll() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DurableControll(String durable_code, String durable_name, String durable_number,
			String durable_brandname, String durable_model, String durable_price, String durable_statusnow,
			String responsible_person, String durable_image, String durable_Borrow_Status,
			String durable_entrancedate) {
		super(durable_code, durable_name, durable_number, durable_brandname, durable_model, durable_price, durable_statusnow,
				responsible_person, durable_image, durable_Borrow_Status, durable_entrancedate);
	}

	public DurableControll(String durable_type, String durable_serial_number,
			String durable_optain_type, String durable_petition_number, String durable_money_type) {
		super();
		this.durable_type = durable_type;
		this.durable_serial_number = durable_serial_number;
		this.durable_optain_type = durable_optain_type;
		this.durable_petition_number = durable_petition_number;
		this.durable_money_type = durable_money_type;
	}


	public String getDurable_type() {
		return durable_type;
	}

	public void setDurable_type(String durable_type) {
		this.durable_type = durable_type;
	}

	public String getDurable_serial_number() {
		return durable_serial_number;
	}

	public void setDurable_serial_number(String durable_serial_number) {
		this.durable_serial_number = durable_serial_number;
	}

	public String getDurable_optain_type() {
		return durable_optain_type;
	}

	public void setDurable_optain_type(String durable_optain_type) {
		this.durable_optain_type = durable_optain_type;
	}

	public String getDurable_petition_number() {
		return durable_petition_number;
	}

	public void setDurable_petition_number(String durable_petition_number) {
		this.durable_petition_number = durable_petition_number;
	}

	public String getDurable_money_type() {
		return durable_money_type;
	}

	public void setDurable_money_type(String durable_money_type) {
		this.durable_money_type = durable_money_type;
	}

	public int getDurable_life_time() {
		return durable_life_time;
	}

	public void setDurable_life_time(int durable_life_time) {
		this.durable_life_time = durable_life_time;
	}

//	public Durable getDurable() {
//		return Durable;
//	}
//
//	public void setDurable(Durable durable) {
//		Durable = durable;
//	}



//	public List<Depreciations> getListDepreciations() {
//		return listDepreciations;
//	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getBudget_year() {
		return budget_year;
	}

	public void setBudget_year(String budget_year) {
		this.budget_year = budget_year;
	}

	public Double getDepreciation_rate() {
		return depreciation_rate;
	}

	public void setDepreciation_rate(Double depreciation_rate) {
		this.depreciation_rate = depreciation_rate;
	}
	
	

	//public void setListDepreciations(List<Depreciations> listDepreciations) {
//		this.listDepreciations = listDepreciations;
//	}

	
	
}
