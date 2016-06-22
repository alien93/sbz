package projara.util.json.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillInfo implements Serializable{

	private int billId;
	
	private Date date;
	private double originalTotal;
	private String state;
	private CustomerBasicInfo customer;
	
	private List<BillDiscountInfo> billDiscounts;
	private List<BillitemInfo> billItems;
	
	
	private List<BillCostInfo> costInfos;
	
	public BillInfo() {
		costInfos = new ArrayList<BillCostInfo>();
	}

	public BillInfo(int billId, List<BillCostInfo> costInfos) {
		super();
		this.billId = billId;
		this.costInfos = costInfos;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public List<BillCostInfo> getCostInfos() {
		return costInfos;
	}

	public void setCostInfos(List<BillCostInfo> costInfos) {
		this.costInfos = costInfos;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getOriginalTotal() {
		return originalTotal;
	}

	public void setOriginalTotal(double originalTotal) {
		this.originalTotal = originalTotal;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public CustomerBasicInfo getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerBasicInfo customer) {
		this.customer = customer;
	}

	public List<BillDiscountInfo> getBillDiscounts() {
		return billDiscounts;
	}

	public void setBillDiscounts(List<BillDiscountInfo> billDiscounts) {
		this.billDiscounts = billDiscounts;
	}

	public List<BillitemInfo> getBillItems() {
		return billItems;
	}

	public void setBillItems(List<BillitemInfo> billItems) {
		this.billItems = billItems;
	}

	public BillInfo(int billId, Date date, double originalTotal, String state,
			CustomerBasicInfo customer, List<BillDiscountInfo> billDiscounts,
			List<BillitemInfo> billItems, List<BillCostInfo> costInfos) {
		super();
		this.billId = billId;
		this.date = date;
		this.originalTotal = originalTotal;
		this.state = state;
		this.customer = customer;
		this.billDiscounts = billDiscounts;
		this.billItems = billItems;
		this.costInfos = costInfos;
	}
	
	

}
