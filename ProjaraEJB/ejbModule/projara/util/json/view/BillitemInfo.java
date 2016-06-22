package projara.util.json.view;

import java.io.Serializable;
import java.util.List;

public class BillitemInfo implements Serializable {

	private int billId;
	private int number;
	
	private ItemInfo item;
	private int quantity;
	
	private double originalCost;
	private double totalCost;
	private double discountPercentage;
	
	private List<BillItemDiscountInfo> itemDiscounts;
	
	public BillitemInfo() {
		// TODO Auto-generated constructor stub
	}

	public BillitemInfo(int billId, int number, ItemInfo item, int quantity,
			double originalCost, double totalCost, double discountPercentage,
			List<BillItemDiscountInfo> itemDiscounts) {
		super();
		this.billId = billId;
		this.number = number;
		this.item = item;
		this.quantity = quantity;
		this.originalCost = originalCost;
		this.totalCost = totalCost;
		this.discountPercentage = discountPercentage;
		this.itemDiscounts = itemDiscounts;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public ItemInfo getItem() {
		return item;
	}

	public void setItem(ItemInfo item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getOriginalCost() {
		return originalCost;
	}

	public void setOriginalCost(double originalCost) {
		this.originalCost = originalCost;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public List<BillItemDiscountInfo> getItemDiscounts() {
		return itemDiscounts;
	}

	public void setItemDiscounts(List<BillItemDiscountInfo> itemDiscounts) {
		this.itemDiscounts = itemDiscounts;
	}
	
	

}
