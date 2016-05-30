/***********************************************************************
 * Module:  CustomerCategory.java
 * Author:  Stanko
 * Purpose: Defines the Class CustomerCategory
 ***********************************************************************/

package projara.model.users;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** @pdOid 4b5b8702-abc4-499e-9efc-3e8f2a73fb4a */
@Entity
@Table(name = "CUSTOMER_CATEGORY")
public class CustomerCategory implements Serializable {
	/** @pdOid e32533f7-f782-4a82-9ec6-719e9c0dda5b */
	@Id
	@Column(name = "CAT_ID", nullable = false, unique = true, columnDefinition = "char(1)")
	private String categoryCode;
	/** @pdOid 8cc2e116-dc11-4669-86bd-546326f8b69f */
	@Column(name = "CAT_NAME", nullable = false, unique = false, columnDefinition = "varchar(40)")
	private String name;

	/**
	 * @pdRoleInfo migr=no name=Threshold assc=hasThresholds coll=Set
	 *             impl=HashSet mult=0..* side=A
	 */
	@ManyToMany
	@JoinTable(name = "HAS_THRESHOLDS", joinColumns = @JoinColumn(name = "CAT_ID", referencedColumnName = "CAT_ID", columnDefinition = "char(1)", nullable = false), inverseJoinColumns = @JoinColumn(name = "THRES_ID", referencedColumnName = "THRES_ID", nullable = false, columnDefinition = "smallint)"))
	private Set<Threshold> thresholds = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="category")
	private Set<Customer> customers = new HashSet<>();

	/** @pdOid c5b2aa2f-e5ae-4b5c-a831-acfbe4180200 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * @param newCategoryCode
	 * @pdOid 95a16977-6e15-46ee-ac48-d79595ca2ae7
	 */
	public void setCategoryCode(String newCategoryCode) {
		categoryCode = newCategoryCode;
	}

	/** @pdOid feba07e3-7c11-479b-9263-5235ea6e304d */
	public String getName() {
		return name;
	}

	/**
	 * @param newName
	 * @pdOid 7d0ff594-7e54-489c-b380-a362bce8b891
	 */
	public void setName(String newName) {
		name = newName;
	}

	/** @pdGenerated default getter */
	public Set<Threshold> getThresholds() {
		if (thresholds == null)
			thresholds = new HashSet<Threshold>();
		return thresholds;
	}

	/** @pdGenerated default iterator getter */
	public Iterator getIteratorThresholds() {
		if (thresholds == null)
			thresholds = new HashSet<Threshold>();
		return thresholds.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newThresholds
	 */
	public void setThresholds(Set<Threshold> newThresholds) {
		removeAllThresholds();
		for (Iterator iter = newThresholds.iterator(); iter.hasNext();)
			addThresholds((Threshold) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newThreshold
	 */
	public void addThresholds(Threshold newThreshold) {
		if (newThreshold == null)
			return;
		if (this.thresholds == null)
			this.thresholds = new HashSet<Threshold>();
		if (!this.thresholds.contains(newThreshold)) {
			this.thresholds.add(newThreshold);
			newThreshold.addCategories(this);
		}
	}

	/**
	 * @pdGenerated default remove
	 * @param oldThreshold
	 */
	public void removeThresholds(Threshold oldThreshold) {
		if (oldThreshold == null)
			return;
		if (this.thresholds != null)
			if (this.thresholds.contains(oldThreshold)) {
				this.thresholds.remove(oldThreshold);
				oldThreshold.removeCategories(this);
			}
	}

	/** @pdGenerated default removeAll */
	public void removeAllThresholds() {
		if (thresholds != null) {
			Threshold oldThreshold;
			for (Iterator iter = getIteratorThresholds(); iter.hasNext();) {
				oldThreshold = (Threshold) iter.next();
				iter.remove();
				oldThreshold.removeCategories(this);
			}
		}
	}


	public Set<Customer> getCustomers() {
		if (customers == null)
			customers = new HashSet<Customer>();
		return customers;
	}


	public Iterator getIteratorCustomers() {
		if (customers == null)
			customers = new HashSet<Customer>();
		return customers.iterator();
	}


	public void setCustomers(Set<Customer> newCustomers) {
		removeAllCustomers();
		for (Iterator iter = newCustomers.iterator(); iter.hasNext();)
			addCustomers((Customer) iter.next());
	}


	public void addCustomers(Customer newCustomer) {
		if (newCustomer == null)
			return;
		if (this.customers == null)
			this.customers = new HashSet<Customer>();
		if (!this.customers.contains(newCustomer)) {
			this.customers.add(newCustomer);
			newCustomer.setCategory(this);
		}
	}


	public void removeCustomers(Customer oldCustomer) {
		if (oldCustomer == null)
			return;
		if (this.customers != null)
			if (this.customers.contains(oldCustomer)) {
				this.customers.remove(oldCustomer);
				oldCustomer.setCategory((CustomerCategory) null);
			}
	}


	public void removeAllCustomers() {
		if (customers != null) {
			Customer oldCustomer;
			for (Iterator iter = getIteratorCustomers(); iter.hasNext();) {
				oldCustomer = (Customer) iter.next();
				iter.remove();
				oldCustomer.setCategory((CustomerCategory) null);
			}
		}
	}

	public CustomerCategory(String categoryCode, String name) {
		super();
		this.categoryCode = categoryCode;
		this.name = name;
	}

	public CustomerCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categoryCode == null) ? 0 : categoryCode.hashCode());
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
		if (!(obj instanceof CustomerCategory)) {
			return false;
		}
		CustomerCategory other = (CustomerCategory) obj;
		if (categoryCode == null) {
			if (other.categoryCode != null) {
				return false;
			}
		} else if (!categoryCode.equals(other.categoryCode)) {
			return false;
		}
		return true;
	}
	

	
	

}