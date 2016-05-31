/***********************************************************************
 * Module:  Item.java
 * Author:  Stanko
 * Purpose: Defines the Class Item
 ***********************************************************************/

package projara.model.items;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import projara.model.shop.Bill;
import projara.model.shop.BillItem;

/** @pdOid e57bd824-c576-430b-bece-d500604bfcf0 */
@Entity
@Table(name = "ITEM")
public class Item implements Serializable {
	/** @pdOid 8dad3df8-c3ec-4c71-8a6e-244868d2e25e */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IT_ID", nullable = false, unique = true)
	private int id;
	/** @pdOid 21cd9c0f-ee51-4113-a854-5acb59900162 */
	@Column(name = "IT_NAME",nullable = false,unique=false,columnDefinition="varchar(60)")
	private String name;
	/** @pdOid 586e348c-6b8f-42a7-bb1b-d822ec5870ad */
	@Column(name = "IT_PRICE",nullable = false, unique = false, columnDefinition="decimal(8,2) default 0.0")
	private double price = 0.0;
	/** @pdOid fbf7c284-e217-4538-bdad-aeb919efdb9a */
	@Column(name = "IT_INSTOCK", nullable=true, unique = false, columnDefinition="default 0")
	private int inStock;
	/** @pdOid c5127174-1ca2-4f4a-a080-5f832fdb2d69 */
	@Column(name = "IT_CREATEDON", nullable=false, unique = false,updatable = false)
	private Date createdOn;
	/** @pdOid e0e6bdb8-4e31-472d-bf62-3f8f1c92ff2d */
	@Column(name = "IT_ISLOW", nullable= true, unique = false, columnDefinition="default false")
	private boolean needOrdering;
	/** @pdOid 26718804-e665-474c-b35e-dcdf37b6b242 */
	@Column(name = "IT_RECSTATE", nullable= true, unique = false, columnDefinition="default false")
	private boolean recordState;
	/** @pdOid 8a74dbe9-dbc9-423d-893f-90d503466737 */
	@Column(name = "IT_MINQUANT", nullable = false, unique = false, columnDefinition="default 0")
	private int minQuantity = 0;

	/** @pdRoleInfo migr=no name=ItemCategory assc=itemCategory mult=1..1 side=A */
	@ManyToOne
	@JoinColumn(name = "ITCAT_CODE", nullable = false, updatable = true, columnDefinition="char(3)")
	private ItemCategory category;
	
	/*PODESI*/
	@OneToMany(mappedBy="item",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<BillItem> items = new HashSet<>();

	/** @pdOid 586f979d-033f-4952-961e-9e62f92469a1 */
	public int getId() {
		return id;
	}

	/**
	 * @param newId
	 * @pdOid 328dc462-4ee1-4a99-8c03-e4fcce46754e
	 */
	public void setId(int newId) {
		id = newId;
	}

	/** @pdOid fe3e07d1-2816-465d-bcec-7c75fd407813 */
	public String getName() {
		return name;
	}

	/**
	 * @param newName
	 * @pdOid 600a9ec7-1f6c-4799-a691-1fad6f1089e2
	 */
	public void setName(String newName) {
		name = newName;
	}

	/** @pdOid 59e67162-ce3a-4821-9905-cc509f8b6e9c */
	public double getPrice() {
		return price;
	}

	/**
	 * @param newPrice
	 * @pdOid 397fc980-a0d9-48d9-be8f-3cefcd99217e
	 */
	public void setPrice(double newPrice) {
		price = newPrice;
	}

	/** @pdOid 0f6c9f69-3e83-4912-9245-72896fa82053 */
	public int getInStock() {
		return inStock;
	}

	/**
	 * @param newInStock
	 * @pdOid 00bd2d58-5579-41c5-b921-032b1c637aea
	 */
	public void setInStock(int newInStock) {
		inStock = newInStock;
	}

	/** @pdOid 6250c152-0234-44e4-891a-27512447996d */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param newCreatedOn
	 * @pdOid 633188ad-c6c9-4df3-8bcb-d17594a18bb8
	 */
	public void setCreatedOn(Date newCreatedOn) {
		createdOn = newCreatedOn;
	}

	/** @pdOid b5c380f7-6193-402e-bff4-6647cef49335 */
	public boolean getNeedOrdering() {
		return needOrdering;
	}

	/**
	 * @param newNeedOrdering
	 * @pdOid 8c2bed4b-35fc-4f8f-b78c-05eb13b327c8
	 */
	public void setNeedOrdering(boolean newNeedOrdering) {
		needOrdering = newNeedOrdering;
	}

	/** @pdOid b7b097df-1ae2-446b-8927-106b418d9411 */
	public boolean getRecordState() {
		return recordState;
	}

	/**
	 * @param newRecordState
	 * @pdOid b2bfd678-e16b-4e1d-a402-c90cf9840864
	 */
	public void setRecordState(boolean newRecordState) {
		recordState = newRecordState;
	}

	/** @pdOid e61162d5-79e8-460e-b5c8-3d08c50de285 */
	public int getMinQuantity() {
		return minQuantity;
	}

	/**
	 * @param newMinQuantity
	 * @pdOid 2603a15b-63ec-4d95-850b-b32ba2b705d2
	 */
	public void setMinQuantity(int newMinQuantity) {
		minQuantity = newMinQuantity;
	}

	/** @pdGenerated default parent getter */
	public ItemCategory getCategory() {
		return category;
	}

	/**
	 * @pdGenerated default parent setter
	 * @param newItemCategory
	 */
	public void setCategory(ItemCategory newItemCategory) {
		if (this.category == null || !this.category.equals(newItemCategory)) {
			if (this.category != null) {
				ItemCategory oldItemCategory = this.category;
				this.category = null;
				oldItemCategory.removeItems(this);
			}
			if (newItemCategory != null) {
				this.category = newItemCategory;
				this.category.addItems(this);
			}
		}
	}
	

	public Set<BillItem> getItems() {
		if (items == null)
			items = new HashSet<BillItem>();
		return items;
	}


	public Iterator getIteratorItems() {
		if (items == null)
			items = new HashSet<BillItem>();
		return items.iterator();
	}


	public void setItems(Set<BillItem> newItems) {
		removeAllItems();
		for (Iterator iter = newItems.iterator(); iter.hasNext();)
			addItems((BillItem) iter.next());
	}


	public void addItems(BillItem newBillItem) {
		if (newBillItem == null)
			return;
		if (this.items == null)
			this.items = new HashSet<BillItem>();
		if (!this.items.contains(newBillItem)) {
			this.items.add(newBillItem);
			newBillItem.setItem(this);
		}
	}


	public void removeItems(BillItem oldBillItem) {
		if (oldBillItem == null)
			return;
		if (this.items != null)
			if (this.items.contains(oldBillItem)) {
				this.items.remove(oldBillItem);
				oldBillItem.setItem((Item) null);
			}
	}


	public void removeAllItems() {
		if (items != null) {
			BillItem oldBillItem;
			for (Iterator iter = getIteratorItems(); iter.hasNext();) {
				oldBillItem = (BillItem) iter.next();
				iter.remove();
				oldBillItem.setItem((Item) null);
			}
		}
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
		if (!(obj instanceof Item)) {
			return false;
		}
		Item other = (Item) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
	
	@PrePersist
	public void setDateBeforePersist(){
		if(createdOn == null)
			createdOn = new Date();
	}

	public Item(String name, double price, int inStock) {
		super();
		this.name = name;
		this.price = price;
		this.inStock = inStock;
	}

	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Item(String name, double price, int inStock, ItemCategory category) {
		super();
		this.name = name;
		this.price = price;
		this.inStock = inStock;
		setCategory(category);
	}
	
	
	

}