/***********************************************************************
 * Module:  Bill.java
 * Author:  Stanko
 * Purpose: Defines the Class Bill
 ***********************************************************************/

package projara.model.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import projara.model.items.Item;
import projara.model.users.Customer;

/** @pdOid 7a28ee07-5380-4cc5-a0b6-958385abdcac */
@Entity
@Table(name = "BILL")
@NamedQueries({
		@NamedQuery(name = "getOrderedBills", query = "SELECT b FROM Bill b WHERE b.state LIKE 'O'"),
		@NamedQuery(name = "getSuccessfulBillsByUser", query = "SELECT b FROM Bill b "
				+ "WHERE b.customer = :myUser AND b.state LIKE 'S'"),
		@NamedQuery(name = "getCancelledByUser", query = "SELECT b FROM Bill b "
				+ "WHERE b.customer = :myUser AND b.state LIKE 'C'"),
		@NamedQuery(name = "getUserHistory", query = "SELECT b FROM Bill b "
				+ "WHERE b.customer = :myUser AND b.state NOT LIKE 'T'"),
		@NamedQuery (name = "getAll",query="SELECT b FROM Bill b "
				+ "WHERE b.state NOT LIKE 'T'"),
		@NamedQuery(name = "getByState", query = "SELECT b FROM Bill b WHERE "
				+ "b.state LIKE :state"),
		@NamedQuery(name = "getByStateAndUser", query = "SELECT b FROM Bill b "
						+ "WHERE b.customer = :myUser AND b.state LIKE :state"),
})
public class Bill implements Serializable {

	/** @pdOid 83da2860-1180-4c41-8949-0f099fcc28a3 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BILL_ID", nullable = false, unique = true)
	private int id;
	/** @pdOid b11df14e-536b-41d0-97ec-61ddcefe88ce */
	@Column(name = "BILL_DATE", nullable = false, unique = false)
	private Date date;
	/** @pdOid 6dc57c81-21ba-42f2-be2d-cb21c7dba636 */
	@Column(name = "BILL_ORTOTAL", nullable = true, unique = false, columnDefinition = "decimal(10,2) default 0.0")
	private double originalTotal;
	/** @pdOid 886784e7-cb0d-4791-972e-b33c287f2ca2 */
	@Column(name = "BILL_DISCPERC", nullable = true, unique = false, columnDefinition = "numeric(5,2) default 0.0")
	private double discountPercentage = 0;
	/** @pdOid fe7f2e24-9b8a-4324-a7fe-cc82860a9de9 */
	@Column(name = "BILL_TOTAL", nullable = true, unique = false, columnDefinition = "decimal(10,2) default 0.0")
	private double total;
	/** @pdOid 70d53dbb-e893-4d5a-8865-6184553f15be */
	@Column(name = "BILL_SPOINTS", nullable = true, unique = false, columnDefinition = "smallint default 0")
	private short spentPoints = 0;
	/** @pdOid 5136f11f-e826-4363-87ef-241d421539f7 */
	@Column(name = "BILL_APOINTS", nullable = true, unique = false, columnDefinition = "smallint default 0")
	private short awardPoints = 0;
	/** @pdOid 97a889d6-f609-40d5-94f2-dac9769f1ce6 */
	@Column(name = "BILL_STATE", nullable = false, unique = false, columnDefinition = "char(1) default 'T'")
	private String state = "T";

	@ManyToOne
	@JoinColumn(name = "USR_ID", nullable = false)
	private Customer customer;

	/**
	 * @pdRoleInfo migr=no name=BillItem assc=hasItems coll=Set impl=HashSet
	 *             mult=0..* type=Composition
	 */
	@OneToMany(mappedBy = "bill", cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	private Set<BillItem> items = new HashSet<>();
	/**
	 * @pdRoleInfo migr=no name=BillDiscount assc=hasDiscount coll=Set
	 *             impl=HashSet mult=0..*
	 */
	@OneToMany(mappedBy = "bill", cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	private Set<BillDiscount> billDiscounts = new HashSet<>();

	/** @pdGenerated default getter */
	public Set<BillItem> getItems() {
		if (items == null)
			items = new HashSet<BillItem>();
		return items;
	}

	/** @pdGenerated default iterator getter */
	public Iterator getIteratorItems() {
		if (items == null)
			items = new HashSet<BillItem>();
		return items.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newItems
	 */
	public void setItems(Set<BillItem> newItems) {
		removeAllItems();
		for (Iterator iter = newItems.iterator(); iter.hasNext();)
			addItems((BillItem) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newBillItem
	 */
	public void addItems(BillItem newBillItem) {
		if (newBillItem == null)
			return;
		if (this.items == null)
			this.items = new HashSet<BillItem>();
		if (!this.items.contains(newBillItem)) {
			this.items.add(newBillItem);
			newBillItem.setBill(this);
		}
	}

	/**
	 * @pdGenerated default remove
	 * @param oldBillItem
	 */
	public void removeItems(BillItem oldBillItem) {
		if (oldBillItem == null)
			return;
		if (this.items != null)
			if (this.items.contains(oldBillItem)) {
				this.items.remove(oldBillItem);
				oldBillItem.setBill((Bill) null);
			}
	}

	/** @pdGenerated default removeAll */
	public void removeAllItems() {
		if (items != null) {
			BillItem oldBillItem;
			for (Iterator iter = getIteratorItems(); iter.hasNext();) {
				oldBillItem = (BillItem) iter.next();
				iter.remove();
				oldBillItem.setBill((Bill) null);
			}
		}
	}

	/** @pdGenerated default getter */
	public Set<BillDiscount> getBillDiscounts() {
		if (billDiscounts == null)
			billDiscounts = new HashSet<BillDiscount>();
		return billDiscounts;
	}

	/** @pdGenerated default iterator getter */
	public Iterator getIteratorBillDiscounts() {
		if (billDiscounts == null)
			billDiscounts = new HashSet<BillDiscount>();
		return billDiscounts.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newBill
	 *            discounts
	 */
	public void setBillDiscounts(Set<BillDiscount> newBillDiscounts) {
		removeAllBillDiscounts();
		for (Iterator iter = newBillDiscounts.iterator(); iter.hasNext();)
			addBillDiscounts((BillDiscount) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newBillDiscount
	 */
	public void addBillDiscounts(BillDiscount newBillDiscount) {
		if (newBillDiscount == null)
			return;
		if (this.billDiscounts == null)
			this.billDiscounts = new HashSet<BillDiscount>();
		if (!this.billDiscounts.contains(newBillDiscount)) {
			this.billDiscounts.add(newBillDiscount);
			newBillDiscount.setBill(this);
		}
	}

	/**
	 * @pdGenerated default remove
	 * @param oldBillDiscount
	 */
	public void removeBillDiscounts(BillDiscount oldBillDiscount) {
		if (oldBillDiscount == null)
			return;
		if (this.billDiscounts != null)
			if (this.billDiscounts.contains(oldBillDiscount)) {
				this.billDiscounts.remove(oldBillDiscount);
				oldBillDiscount.setBill((Bill) null);
			}
	}

	/** @pdGenerated default removeAll */
	public void removeAllBillDiscounts() {
		if (billDiscounts != null) {
			BillDiscount oldBillDiscount;
			for (Iterator iter = getIteratorBillDiscounts(); iter.hasNext();) {
				oldBillDiscount = (BillDiscount) iter.next();
				iter.remove();
				oldBillDiscount.setBill((Bill) null);
			}
		}
	}

	/** @pdOid 4c15dea3-6386-4b35-8ccc-a120704c64ad */
	public int getId() {
		return id;
	}

	/**
	 * @param newId
	 * @pdOid a2a7c425-0811-4bf9-ba35-e6e0b59ef550
	 */
	public void setId(int newId) {
		id = newId;
	}

	/** @pdOid 2988db1d-5bc7-489c-850c-23fbf0442474 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param newDate
	 * @pdOid f2c06c97-7de0-47df-9001-80ed700995d0
	 */
	public void setDate(Date newDate) {
		date = newDate;
	}

	/** @pdOid 18233175-710d-4637-8d67-3974b0080f08 */
	public double getOriginalTotal() {
		return originalTotal;
	}

	/**
	 * @param newOriginalTotal
	 * @pdOid 1f429728-e5d5-4e6d-9945-f7624a3deaf8
	 */
	public void setOriginalTotal(double newOriginalTotal) {
		originalTotal = newOriginalTotal;
	}

	/** @pdOid 6b2d7818-f19b-49c1-8902-f4503ebec5f9 */
	public double getDiscountPercentage() {
		return discountPercentage;
	}

	/**
	 * @param newDiscountPercentage
	 * @pdOid 697b37de-cefc-450e-8cc2-6cf7763f7351
	 */
	public void setDiscountPercentage(double newDiscountPercentage) {
		discountPercentage = newDiscountPercentage;
	}

	/** @pdOid 00883ec4-57ae-4a38-97cd-e91f1ebdc82a */
	public double getTotal() {
		return total;
	}

	/**
	 * @param newTotal
	 * @pdOid 30d6f1cd-7a18-4d56-8bae-64c4306fffbc
	 */
	public void setTotal(double newTotal) {
		total = newTotal;
	}

	/** @pdOid d50a5211-f6f8-4905-9e92-f4cc09207ac9 */
	public short getSpentPoints() {
		return spentPoints;
	}

	/**
	 * @param newSpentPoints
	 * @pdOid 559748fc-5f1e-4910-acd6-73afa7974208
	 */
	public void setSpentPoints(short newSpentPoints) {
		spentPoints = newSpentPoints;
	}

	/** @pdOid 66616d7d-ffb6-46c3-86c4-ad83ae16aefb */
	public short getAwardPoints() {
		return awardPoints;
	}

	/**
	 * @param newAwardPoints
	 * @pdOid 54d46499-c17c-4095-aa52-a88d29635f38
	 */
	public void setAwardPoints(short newAwardPoints) {
		awardPoints = newAwardPoints;
	}

	/** @pdOid 930a3d87-62f2-4e64-a796-ad11282f991d */
	public String getState() {
		return state;
	}

	/**
	 * @param newState
	 * @pdOid 139b42c1-ca9d-4f14-89c4-a9561c328496
	 */
	public void setState(String newState) {
		state = newState;
	}

	public void setCustomer(Customer newCustomer) {
		if (this.customer == null || !this.customer.equals(newCustomer)) {
			if (this.customer != null) {
				Customer oldCustomer = this.customer;
				this.customer = null;
				oldCustomer.removeBills(this);
			}
			if (newCustomer != null) {
				this.customer = newCustomer;
				this.customer.addBills(this);
			}
		}
	}

	public Set<BillItem> getBillItems() {
		if (items == null)
			items = new HashSet<BillItem>();
		return items;
	}

	public Iterator getIteratorBillItems() {
		if (items == null)
			items = new HashSet<BillItem>();
		return items.iterator();
	}

	public void setBillItems(Set<BillItem> newBillItems) {
		removeAllBillItems();
		for (Iterator iter = newBillItems.iterator(); iter.hasNext();)
			addBillItems((BillItem) iter.next());
	}

	public void addBillItems(BillItem newBillItem) {
		if (newBillItem == null)
			return;
		if (this.items == null)
			this.items = new HashSet<BillItem>();
		if (!this.items.contains(newBillItem)) {
			this.items.add(newBillItem);
			newBillItem.setBill(this);
		}
	}

	public void removeBillItems(BillItem oldBillItem) {
		if (oldBillItem == null)
			return;
		if (this.items != null)
			if (this.items.contains(oldBillItem)) {
				this.items.remove(oldBillItem);
				oldBillItem.setBill((Bill) null);
			}
	}

	public void removeAllBillItems() {
		if (items != null) {
			BillItem oldBilltem;
			for (Iterator iter = getIteratorBillItems(); iter.hasNext();) {
				oldBilltem = (BillItem) iter.next();
				iter.remove();
				oldBilltem.setBill((Bill) null);
			}
		}
	}

	public Customer getCustomer() {
		return customer;
	}

	@PrePersist
	public void setDateOfBill() {
		if (date == null)
			date = new Date();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		if (!(obj instanceof Bill)) {
			return false;
		}
		Bill other = (Bill) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
	
	/**
	 * @author Nina
	 * Ukoliko u računu postoje barem 10 artikala čija ukupna cena prelazi 
	 * 50% cene ukupne vrednosti naručenih artikala, metoda vraca true
	 * @return
	 */
	public boolean itemsPercentageTest(){
		boolean retVal = false;
		if(items.isEmpty() || getNoItems(items)<10){			  //nema 10 artikala, vrati false
			retVal = false;
		}
		else if(getNoItems(items) >= 10 && getNoItems(items)<20){ //ukupna cena sigurno prelazi 50% ukupne vrednosti, vrati true
			retVal = true;
		}
		else{
			//sortiraj iteme po ceni
			ArrayList<Double> itemsByPrice = sortItemsByPrice(items);
			//nadji sumu prvih 10
			double sum = 0;
			for(int i=itemsByPrice.size()-1; i>=0; i--){
				sum+= itemsByPrice.get(i);
			}
			//proveri da li je suma veca od 50% ukupne sume
			if(sum > total/2){
				retVal = true;
			}
			else{
				retVal = false;
			}
		}
		
		return retVal;
	}
	/**
	 * @author Nina
	 * Sortira artikle na racunu po ceni
	 * @param items
	 * @return
	 */
	private ArrayList<Double> sortItemsByPrice(Set<BillItem> items){
		ArrayList<Double> itemsByPrice = new ArrayList<>();
		for(BillItem item: items){
			itemsByPrice.add(item.getTotal());
		}
		Collections.sort(itemsByPrice);
		System.out.println(itemsByPrice);
		return itemsByPrice;
	}
	
	/**
	 * @author Nina
	 * @return broj artikala na racunu
	 */
	private Integer getNoItems(Set<BillItem> items){
		Integer retVal = 0;
		for(BillItem item: items){
			retVal += ((Double)item.getQuantity()).intValue();
		}
		return retVal;
	}
	
	/**
	 * @author Nina
	 * Racuna sumu na osnovu cena item-a
	 */
	private Double getSum(Set<BillItem> items){
		Double retVal = 0.0;
		for(BillItem item: items){
			retVal += ((Double)item.getQuantity()*item.getPrice());
		}
		return retVal;
	}

	public Bill() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bill(double originalTotal, double total, String state) {
		super();
		this.originalTotal = originalTotal;
		this.total = total;
		this.state = state;
	}

	public Bill(double originalTotal, double total, String state,
			Customer customer) {
		super();
		this.originalTotal = originalTotal;
		this.total = total;
		this.state = state;
		setCustomer(customer);
	}

	public Bill(String state, Customer customer) {
		super();
		this.state = state;
		setCustomer(customer);
	}
	/**
	 * @author Nina
	 * Test konstruktor
	 * @param total
	 */
	@Deprecated
	public Bill(double origTotal) {
		super();
		this.items = new HashSet<BillItem>();
		
		//test treba da prodje
		for(int i=0; i<20; i++){
			BillItem b = new BillItem(1000, 1, new Item());
			b.setId(new BillItemPK(i, i+1));
			b.setTotal(1000);
			items.add(b);
		}
		BillItem b = new BillItem(30001, 1, new Item());
		b.setId(new BillItemPK(50, 50));
		b.setTotal(30000);
		items.add(b);
		
		
		//test ne treba da prodje
		/*for(int i=0; i<20; i++){
			BillItem b = new BillItem(1, 1, new Item());
			b.setId(new BillItemPK(i, i+1));
			this.setTotal(1);
			this.items.add(b);
		}*/
		
		//test treba da prodje
		/*BillItem bi = new BillItem(10000.0, 10, new Item());
		this.items.add(bi);*/
		
		//test ne treba da prodje
		/*BillItem bi = new BillItem(100.0, 9, new Item());
		this.items.add(bi);*/
		this.originalTotal = getSum(items);
	}

	/*
	@PreRemove
	public void preRemoveBill(){
		removeAllBillDiscounts();
		removeAllBillItems();
		getCustomer().removeBills(this);
	}
	*/

}