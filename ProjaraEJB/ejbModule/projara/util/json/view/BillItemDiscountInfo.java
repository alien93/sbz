package projara.util.json.view;

import java.io.Serializable;

public class BillItemDiscountInfo implements Serializable {

	private int id;
	private double percentage;
	private String type;
	private String name;
	
	public BillItemDiscountInfo() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public BillItemDiscountInfo(int id, double percentage, String type,String name) {
		super();
		this.id = id;
		this.percentage = percentage;
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
