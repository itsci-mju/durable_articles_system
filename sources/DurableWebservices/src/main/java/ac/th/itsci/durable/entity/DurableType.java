package ac.th.itsci.durable.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "durable_type")
public class DurableType {
	@Id
	private String durable_type_name;
	@Column(name = "durable_life_time")
	private int durable_life_time;
	@Column(name = "durable_depreciation")
	private Double durable_depreciation;

	public DurableType(String durable_type_name, int durable_life_time, Double durable_depreciation) {
		super();
		this.durable_type_name = durable_type_name;
		this.durable_life_time = durable_life_time;
		this.durable_depreciation = durable_depreciation;
	}

	public DurableType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDurable_type_name() {
		return durable_type_name;
	}

	public void setDurable_type_name(String durable_type_name) {
		this.durable_type_name = durable_type_name;
	}

	public int getDurable_life_time() {
		return durable_life_time;
	}

	public void setDurable_life_time(int durable_life_time) {
		this.durable_life_time = durable_life_time;
	}

	public Double getDurable_depreciation() {
		return durable_depreciation;
	}

	public void setDurable_depreciation(Double durable_depreciation) {
		this.durable_depreciation = durable_depreciation;
	}

}
