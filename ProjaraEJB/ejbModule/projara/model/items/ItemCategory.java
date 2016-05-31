/***********************************************************************
 * Module:  ItemCategory.java
 * Author:  Stanko
 * Purpose: Defines the Class ItemCategory
 ***********************************************************************/

package projara.model.items;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import projara.model.shop.ActionEvent;

/** @pdOid ee427e5c-a07d-46d8-973e-a8a932f975ea */
@Entity
@Table(name = "ITEM_CATEGORY")
public class ItemCategory implements Serializable {
	/** @pdOid 02aa68bb-53fa-4584-a272-e78dba6eda62 */
	@Id
	@Column(name = "ITCAT_CODE", nullable = false, unique = true, columnDefinition = "char(3)")
	private String code;
	/** @pdOid edc87bd3-30e7-4d5f-8165-94208705276e */
	@Column(name = "ITCAT_NAME", nullable = false, columnDefinition = "varchar(60)")
	private String name;
	/** @pdOid b3304d40-018e-4409-8f81-09346733640b */
	@Column(name = "ITCAT_MDISCOUNT", nullable = false, columnDefinition = "numeric(4,2)")
	private double maxDiscount;

	/**
	 * @pdRoleInfo migr=no name=ActionEvent assc=onDiscount coll=Set
	 *             impl=HashSet mult=0..* side=A
	 */
	@ManyToMany(mappedBy="categories", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ActionEvent> actions = new HashSet<>();

	/**
	 * @pdRoleInfo migr=no name=ItemCategory assc=subcategories coll=Set
	 *             impl=HashSet mult=0..*
	 */
	@OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ItemCategory> subCategories = new HashSet<>();
	/**
	 * @pdRoleInfo migr=no name=Item assc=itemCategory coll=Set impl=HashSet
	 *             mult=0..*
	 */
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Item> items = new HashSet<>();

	/** @pdRoleInfo migr=no name=ItemCategory assc=subcategories mult=0..1 side=A */
	@ManyToOne
	@JoinColumn(name = "ITE_ITCAT_CODE", columnDefinition = "char(3)", nullable = true)
	private ItemCategory parentCategory;

	/** @pdGenerated default getter */
	public Set<ActionEvent> getActions() {
		if (actions == null)
			actions = new HashSet<ActionEvent>();
		return actions;
	}

	/** @pdGenerated default iterator getter */
	public Iterator getIteratorActions() {
		if (actions == null)
			actions = new HashSet<ActionEvent>();
		return actions.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newActions
	 */
	public void setActions(Set<ActionEvent> newActions) {
		removeAllActions();
		for (Iterator iter = newActions.iterator(); iter.hasNext();)
			addActions((ActionEvent) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newActionEvent
	 */
	public void addActions(ActionEvent newActionEvent) {
		if (newActionEvent == null)
			return;
		if (this.actions == null)
			this.actions = new HashSet<ActionEvent>();
		if (!this.actions.contains(newActionEvent)) {
			this.actions.add(newActionEvent);
			newActionEvent.addCategories(this);
		}
	}

	/**
	 * @pdGenerated default remove
	 * @param oldActionEvent
	 */
	public void removeActions(ActionEvent oldActionEvent) {
		if (oldActionEvent == null)
			return;
		if (this.actions != null)
			if (this.actions.contains(oldActionEvent)) {
				this.actions.remove(oldActionEvent);
				oldActionEvent.removeCategories(this);
			}
	}

	/** @pdGenerated default removeAll */
	public void removeAllActions() {
		if (actions != null) {
			ActionEvent oldActionEvent;
			for (Iterator iter = getIteratorActions(); iter.hasNext();) {
				oldActionEvent = (ActionEvent) iter.next();
				iter.remove();
				oldActionEvent.removeCategories(this);
			}
		}
	}

	/** @pdOid fc6eebf3-b0fa-4a23-972d-1935b101a57c */
	public String getCode() {
		return code;
	}

	/**
	 * @param newCode
	 * @pdOid eff932c8-92b6-4a68-8804-74f80ba7f2b9
	 */
	public void setCode(String newCode) {
		code = newCode;
	}

	/** @pdOid 821a070d-d674-40a2-a95a-93516fca4963 */
	public String getName() {
		return name;
	}

	/**
	 * @param newName
	 * @pdOid e30796dc-8293-4b4b-88cd-63f9e18bd934
	 */
	public void setName(String newName) {
		name = newName;
	}

	/** @pdOid 8d66b42e-18c4-4fa6-a854-0d1ad75622b7 */
	public double getMaxDiscount() {
		return maxDiscount;
	}

	/**
	 * @param newMaxDiscount
	 * @pdOid 4c859be9-8e28-4146-97a9-c01e1c6c88e1
	 */
	public void setMaxDiscount(double newMaxDiscount) {
		maxDiscount = newMaxDiscount;
	}

	/** @pdGenerated default getter */
	public Set<ItemCategory> getSubCategories() {
		if (subCategories == null)
			subCategories = new HashSet<ItemCategory>();
		return subCategories;
	}

	/** @pdGenerated default iterator getter */
	public Iterator getIteratorSubCategories() {
		if (subCategories == null)
			subCategories = new HashSet<ItemCategory>();
		return subCategories.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newSubCategories
	 */
	public void setSubCategories(Set<ItemCategory> newSubCategories) {
		removeAllSubCategories();
		for (Iterator iter = newSubCategories.iterator(); iter.hasNext();)
			addSubCategories((ItemCategory) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newItemCategory
	 */
	public void addSubCategories(ItemCategory newItemCategory) {
		if (newItemCategory == null)
			return;
		if (this.subCategories == null)
			this.subCategories = new HashSet<ItemCategory>();
		if (!this.subCategories.contains(newItemCategory)) {
			this.subCategories.add(newItemCategory);
			newItemCategory.setParentCategory(this);
		}
	}

	/**
	 * @pdGenerated default remove
	 * @param oldItemCategory
	 */
	public void removeSubCategories(ItemCategory oldItemCategory) {
		if (oldItemCategory == null)
			return;
		if (this.subCategories != null)
			if (this.subCategories.contains(oldItemCategory)) {
				this.subCategories.remove(oldItemCategory);
				oldItemCategory.setParentCategory((ItemCategory) null);
			}
	}

	/** @pdGenerated default removeAll */
	public void removeAllSubCategories() {
		if (subCategories != null) {
			ItemCategory oldItemCategory;
			for (Iterator iter = getIteratorSubCategories(); iter.hasNext();) {
				oldItemCategory = (ItemCategory) iter.next();
				iter.remove();
				oldItemCategory.setParentCategory((ItemCategory) null);
			}
		}
	}

	/** @pdGenerated default getter */
	public Set<Item> getItems() {
		if (items == null)
			items = new HashSet<Item>();
		return items;
	}

	/** @pdGenerated default iterator getter */
	public Iterator getIteratorItems() {
		if (items == null)
			items = new HashSet<Item>();
		return items.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newItems
	 */
	public void setItems(Set<Item> newItems) {
		removeAllItems();
		for (Iterator iter = newItems.iterator(); iter.hasNext();)
			addItems((Item) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newItem
	 */
	public void addItems(Item newItem) {
		if (newItem == null)
			return;
		if (this.items == null)
			this.items = new HashSet<Item>();
		if (!this.items.contains(newItem)) {
			this.items.add(newItem);
			newItem.setCategory(this);
		}
	}

	/**
	 * @pdGenerated default remove
	 * @param oldItem
	 */
	public void removeItems(Item oldItem) {
		if (oldItem == null)
			return;
		if (this.items != null)
			if (this.items.contains(oldItem)) {
				this.items.remove(oldItem);
				oldItem.setCategory((ItemCategory) null);
			}
	}

	/** @pdGenerated default removeAll */
	public void removeAllItems() {
		if (items != null) {
			Item oldItem;
			for (Iterator iter = getIteratorItems(); iter.hasNext();) {
				oldItem = (Item) iter.next();
				iter.remove();
				oldItem.setCategory((ItemCategory) null);
			}
		}
	}

	/** @pdGenerated default parent getter */
	public ItemCategory getParentCategory() {
		return parentCategory;
	}

	/**
	 * @pdGenerated default parent setter
	 * @param newItemCategory
	 */
	public void setParentCategory(ItemCategory newItemCategory) {
		if (this.parentCategory == null
				|| !this.parentCategory.equals(newItemCategory)) {
			if (this.parentCategory != null) {
				ItemCategory oldItemCategory = this.parentCategory;
				this.parentCategory = null;
				oldItemCategory.removeSubCategories(this);
			}
			if (newItemCategory != null) {
				this.parentCategory = newItemCategory;
				this.parentCategory.addSubCategories(this);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		if (!(obj instanceof ItemCategory)) {
			return false;
		}
		ItemCategory other = (ItemCategory) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		return true;
	}

	public ItemCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemCategory(String code, String name, double maxDiscount) {
		super();
		this.code = code;
		this.name = name;
		this.maxDiscount = maxDiscount;
	}

	public ItemCategory(String code, String name, double maxDiscount,
			ItemCategory parentCategory) {
		super();
		this.code = code;
		this.name = name;
		this.maxDiscount = maxDiscount;
		setParentCategory(parentCategory);
	}
	
	
	

}