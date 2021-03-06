/***********************************************************************
 * Module:  ActionEvent.java
 * Author:  Stanko
 * Purpose: Defines the Class ActionEvent
 ***********************************************************************/

package projara.model.shop;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import projara.model.items.Item;
import projara.model.items.ItemCategory;

/** @pdOid 9416283d-47c2-4bb5-a1df-ab89e894afdd */
@Entity
@Table(name = "ACTION_EVENT")
@NamedQueries({
	@NamedQuery(name = "findActiveEvents", query="SELECT ae FROM ActionEvent ae WHERE "
			+ "ae.from <= :currentDate AND ae.until >= :currentDate")
})
public class ActionEvent implements Serializable {
	/** @pdOid d845848f-251a-41ce-b5da-c4115e751212 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AE_ID", nullable = false, unique = true)
	private int id;
	/** @pdOid bbf42069-d417-48d9-bd49-c9d47e4976c2 */
	@Column(name = "AE_NAME", nullable = false, unique = false, columnDefinition = "varchar(60)")
	private String name;
	/** @pdOid e161ef3e-0ece-42e4-bb61-6176284314e0 */
	@Column(name = "AE_FROM", nullable = false, unique = false)
	private Date from;
	/** @pdOid 28048549-8d43-41c0-8d27-0d6d7de8949a */
	@Column(name = "AE_UNTIL", nullable = false, unique = false)
	private Date until;
	/** @pdOid f2f43726-263d-4040-bbe4-292b1324b755 */
	@Column(name = "AE_DISCOUNT", nullable = false, unique = false)
	private double discount;

	/**
	 * @pdRoleInfo migr=no name=ItemCategory assc=onDiscount coll=Set
	 *             impl=HashSet mult=0..*
	 */
	@ManyToMany
	@JoinTable(name = "ON_DISCOUNT", joinColumns = @JoinColumn(name = "AE_ID", referencedColumnName = "AE_ID", columnDefinition = "int"), inverseJoinColumns = @JoinColumn(name = "ITCAT_CODE", referencedColumnName = "ITCAT_CODE", columnDefinition = "char(3)"))
	private Set<ItemCategory> categories = new HashSet<>();

	/** @pdGenerated default getter */
	public Set<ItemCategory> getCategories() {
		if (categories == null)
			categories = new HashSet<ItemCategory>();
		return categories;
	}

	/** @pdGenerated default iterator getter */
	public Iterator getIteratorCategories() {
		if (categories == null)
			categories = new HashSet<ItemCategory>();
		return categories.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newCategories
	 */
	public void setCategories(Set<ItemCategory> newCategories) {
		removeAllCategories();
		for (Iterator iter = newCategories.iterator(); iter.hasNext();)
			addCategories((ItemCategory) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newItemCategory
	 */
	public void addCategories(ItemCategory newItemCategory) {
		if (newItemCategory == null)
			return;
		if (this.categories == null)
			this.categories = new HashSet<ItemCategory>();
		if (!this.categories.contains(newItemCategory)) {
			this.categories.add(newItemCategory);
			newItemCategory.addActions(this);
		}
	}

	/**
	 * @pdGenerated default remove
	 * @param oldItemCategory
	 */
	public void removeCategories(ItemCategory oldItemCategory) {
		if (oldItemCategory == null)
			return;
		if (this.categories != null)
			if (this.categories.contains(oldItemCategory)) {
				this.categories.remove(oldItemCategory);
				oldItemCategory.removeActions(this);
			}
	}

	/** @pdGenerated default removeAll */
	public void removeAllCategories() {
		if (categories != null) {
			ItemCategory oldItemCategory;
			for (Iterator iter = getIteratorCategories(); iter.hasNext();) {
				oldItemCategory = (ItemCategory) iter.next();
				iter.remove();
				oldItemCategory.removeActions(this);
			}
		}
	}

	/** @pdOid 15ad3fbd-aa02-427f-8cd5-ca2f6fbf6dc6 */
	public int getId() {
		return id;
	}

	/**
	 * @param newId
	 * @pdOid 0b58fffa-975d-4738-a8b7-52fdee7a81ec
	 */
	public void setId(int newId) {
		id = newId;
	}

	/** @pdOid 3cd7c3b4-2c15-4ec4-b041-b6ac1ea73849 */
	public String getName() {
		return name;
	}

	/**
	 * @param newName
	 * @pdOid 44bc4957-c7c7-4077-a82b-731cd30514ca
	 */
	public void setName(String newName) {
		name = newName;
	}

	/** @pdOid 91628d5b-e7ea-43c2-bfdb-70394f478aa5 */
	public Date getFrom() {
		return from;
	}

	/**
	 * @param newFrom
	 * @pdOid c0803d64-b48f-4810-9e40-a91d9bde5257
	 */
	public void setFrom(Date newFrom) {
		from = newFrom;
	}

	/** @pdOid b392ccad-25ee-4cf8-9c94-2b82942c5cc0 */
	public Date getUntil() {
		return until;
	}

	/**
	 * @param newUntil
	 * @pdOid f54c130c-0148-4571-9387-fcc68eb71fea
	 */
	public void setUntil(Date newUntil) {
		until = newUntil;
	}

	/** @pdOid 2b8d5ba9-03f1-4782-944c-33b432785c84 */
	public double getDiscount() {
		return discount;
	}

	/**
	 * @param newDiscount
	 * @pdOid 1072eb32-6cc0-4b8b-8091-c8f684d8becd
	 */
	public void setDiscount(double newDiscount) {
		discount = newDiscount;
	}

	public ActionEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActionEvent(String name, Date from, Date until, double discount) {
		super();
		this.name = name;
		this.from = from;
		this.until = until;
		this.discount = discount;
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
		if (!(obj instanceof ActionEvent)) {
			return false;
		}
		ActionEvent other = (ActionEvent) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
	
	public boolean isOnAction(Item item){
		
		
		for(ItemCategory cat:categories){
			if(item.isCategoryOf(cat))
				return true;
		}
		
		return false;
	}

}