package projara.util.json.view;

import java.io.Serializable;

public class BillCostInfo implements Serializable{

	private int billId;
	private short awardPoints;
	private short spentPoints;
	private double discount;
	private double total;
	
	public BillCostInfo(){
		// TODO Auto-generated constructor stub
	}

	public short getAwardPoints() {
		return awardPoints;
	}

	public void setAwardPoints(short awardPoints) {
		this.awardPoints = awardPoints;
	}

	public short getSpentPoints() {
		return spentPoints;
	}

	public void setSpentPoints(short spentPoints) {
		this.spentPoints = spentPoints;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public BillCostInfo(short awardPoints, short spentPoints, double discount,
			double total,int billId) {
		super();
		this.awardPoints = awardPoints;
		this.spentPoints = spentPoints;
		this.discount = discount;
		this.total = total;
		this.billId = billId;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}
	
	
	

}
