/***********************************************************************
 * Module:  BillItem.java
 * Author:  Stanko
 * Purpose: Defines the Class BillItem
 ***********************************************************************/

package projara.model.shop;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import projara.model.items.Item;
import projara.model.users.Customer;

/** @pdOid e60b0e1d-4b7c-4a41-a770-13911a160730 */

@Entity
@Table(name = "BILL_ITEM")
public class BillItem implements Serializable {
	/** @pdOid 0a6a2e9a-0bd5-4ce8-95c6-620c671e36e7 */
	// private double no;
	@EmbeddedId
	private BillItemPK id;
	
	/** @pdOid 071bff18-0e35-47cb-9930-9ec017b1d89b */
	@Column(name = "BILLIT_PRICE", nullable = false, unique = false, columnDefinition="decimal(10,2) default 0.0")
	private double price = 0.0;
	/** @pdOid cc71a2ca-bc74-479c-8380-cebde16c943a */
	@Column(name = "BILLIT_QUANTITY", nullable = false, unique = false, columnDefinition="numeric(4,0) default 0")
	private double quantity = 0;
	/** @pdOid 95528321-63b3-449e-a520-f8382da72a7e */
	@Column(name = "BILLIT_ORTOTAL", nullable = false, unique = false, columnDefinition="decimal(10,2) default 0")
	private double originalTotal = 0.0;
	/** @pdOid 95bc9e4f-033e-436b-a7cd-01a64037b102 */
	@Column(name = "BILLIT_DISCPERC", nullable = false, unique = false, columnDefinition="numeric(5,2) default 0")
	private double discountPercentage = 0.0;
	/** @pdOid 401fcef4-b8f6-432e-a5d0-01f6692fdad0 */
	@Column(name = "BILLIT_TOTAL", nullable = false, unique = false, columnDefinition="decimal(10,2) default 0")
	private double total = 0.0;

	/**
	 * @pdRoleInfo migr=no name=BillItemDiscount assc=itemHasDiscounts coll=Set
	 *             impl=HashSet mult=0..*
	 */
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="billItem")
	private Set<BillItemDiscount> discounts = new HashSet<>();
	/** @pdRoleInfo migr=no name=Item assc=isOnBill mult=1..1 side=A */
	@ManyToOne
	@JoinColumn(name = "IT_ID",referencedColumnName="IT_ID",nullable = false)
	private Item item;
	/** @pdRoleInfo migr=no name=Bill assc=hasItems mult=1..1 side=A */
	@MapsId("billId")
	@JoinColumn(name = "BILL_ID",referencedColumnName="BILL_ID")
	@ManyToOne
	private Bill bill;

	/** @pdGenerated default getter */
	public Set<BillItemDiscount> getDiscounts() {
		if (discounts == null)
			discounts = new HashSet<BillItemDiscount>();
		return discounts;
	}

	/** @pdGenerated default iterator getter */
	public Iterator getIteratorDiscounts() {
		if (discounts == null)
			discounts = new HashSet<BillItemDiscount>();
		return discounts.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newDiscounts
	 */
	public void setDiscounts(Set<BillItemDiscount> newDiscounts) {
		removeAllDiscounts();
		for (Iterator iter = newDiscounts.iterator(); iter.hasNext();)
			addDiscounts((BillItemDiscount) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newBillItemDiscount
	 */
	public void addDiscounts(BillItemDiscount newBillItemDiscount) {
		if (newBillItemDiscount == null)
			return;
		if (this.discounts == null)
			this.discounts = new HashSet<BillItemDiscount>();
		if (!this.discounts.contains(newBillItemDiscount)) {
			this.discounts.add(newBillItemDiscount);
			newBillItemDiscount.setBillItem(this);
		}
	}

	/**
	 * @pdGenerated default remove
	 * @param oldBillItemDiscount
	 */
	public void removeDiscounts(BillItemDiscount oldBillItemDiscount) {
		if (oldBillItemDiscount == null)
			return;
		if (this.discounts != null)
			if (this.discounts.contains(oldBillItemDiscount)) {
				this.discounts.remove(oldBillItemDiscount);
				oldBillItemDiscount.setBillItem((BillItem) null);
			}
	}

	/** @pdGenerated default removeAll */
	public void removeAllDiscounts() {
		if (discounts != null) {
			BillItemDiscount oldBillItemDiscount;
			for (Iterator iter = getIteratorDiscounts(); iter.hasNext();) {
				oldBillItemDiscount = (BillItemDiscount) iter.next();
				iter.remove();
				oldBillItemDiscount.setBillItem((BillItem) null);
			}
		}
	}
	/** @pdGenerated default parent getter */
	public Bill getBill() {
		return bill;
	}

	/**
	 * @pdGenerated default parent setter
	 * @param newBill
	 */
	public void setBill(Bill newBill) {
		if (this.bill == null || !this.bill.equals(newBill)) {
			if (this.bill != null) {
				Bill oldBill = this.bill;
				this.bill = null;
				oldBill.removeItems(this);
			}
			if (newBill != null) {
				this.bill = newBill;
				this.bill.addItems(this);
			}
		}
	}

	/** @pdOid ce4aba9c-ef63-4df0-8ff3-5062436d47d4 */
	// public double getNo() {
	// return no;
	// }

	/**
	 * @param newNo
	 * @pdOid 0f9d8012-a4d3-4247-979d-65e6a1a9e584
	 */
	// public void setNo(double newNo) {
	// no = newNo;
	// }

	/** @pdOid c477692e-a12f-40e0-b5ec-3b6ee9fac8bd */
	public double getPrice() {
		return price;
	}

	/**
	 * @param newPrice
	 * @pdOid f4f4d936-f3bd-451b-998d-5a715b0bf814
	 */
	public void setPrice(double newPrice) {
		price = newPrice;
	}

	/** @pdOid 3eab79b6-da08-4c92-9e8e-9fcac035abac */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * @param newQuantity
	 * @pdOid f8e397ec-ed3f-47e0-aa54-bc8cbddd2b2f
	 */
	public void setQuantity(double newQuantity) {
		quantity = newQuantity;
	}

	/** @pdOid bb4f3017-dd52-43ed-b03e-4dae54480a30 */
	public double getOriginalTotal() {
		return originalTotal;
	}

	/**
	 * @param newOriginalTotal
	 * @pdOid 7926e861-535d-49c0-8175-38dd403d9a03
	 */
	public void setOriginalTotal(double newOriginalTotal) {
		originalTotal = newOriginalTotal;
	}

	/** @pdOid 2c6b4a73-dda1-4aa1-a9df-c904ffceb1fc */
	public double getDiscountPercentage() {
		return discountPercentage;
	}

	/**
	 * @param newDiscountPercentage
	 * @pdOid d19a86df-540f-429b-897f-e13d78f78fe0
	 */
	public void setDiscountPercentage(double newDiscountPercentage) {
		discountPercentage = newDiscountPercentage;
	}

	/** @pdOid 155612f0-6181-40c9-a516-b026ba4979c6 */
	public double getTotal() {
		return total;
	}

	/**
	 * @param newTotal
	 * @pdOid 8c6d86f2-5c0d-481a-bc21-d532e862c696
	 */
	public void setTotal(double newTotal) {
		total = newTotal;
	}

	public BillItemPK getId() {
		return id;
	}

	public void setId(BillItemPK id) {
		this.id = id;
	}
	
	public void setItem(Item newItem) {
		if (this.item == null || !this.item.equals(newItem)) {
			if (this.item != null) {
				Item oldItem = this.item;
				this.item = null;
				oldItem.removeItems(this);
			}
			if (newItem != null) {
				this.item = newItem;
				this.item.addItems(this);
			}
		}
	}

	public Item getItem() {
		return item;
	}

	public BillItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BillItem)) {
			return false;
		}
		BillItem other = (BillItem) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public BillItem(double price, double quantity, double originalTotal,
			double total, Item item, Bill bill) {
		super();
		this.price = price;
		this.quantity = quantity;
		this.originalTotal = originalTotal;
		this.total = total;
		this.id = new BillItemPK(bill.getId(),bill.getBillItems().size()+1);
		setItem(item);
		setBill(bill);
	}

	public BillItem(double price, double quantity, Item item, Bill bill) {
		super();
		this.price = price;
		this.quantity = quantity;
		this.id = new BillItemPK(bill.getId(),bill.getBillItems().size()+1);
		setItem(item);
		setBill(bill);
	}
	
	public BillItem(double price, int quantity, Item item) {
		this.price = price;
		this.quantity = quantity;
		setItem(item);
	}

	public int getItemNo(){
		return id.getItemNo();
	}
	
	public Customer getCustomer(){
		return getBill().getCustomer();
	}
	
	@PreRemove
	public void preRemoveBillItem(){
		if(bill !=null)
			bill.removeBillItems(this);
		item.removeItems(this);
		removeAllDiscounts();
	}
	
	

}