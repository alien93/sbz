package projara.model.users;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import projara.model.shop.Bill;
import projara.model.shop.BillItem;

@Entity
@DiscriminatorValue(value = "C")
public class Customer extends User implements Serializable {
	@Column(name = "USR_CUS_ADDRESS", nullable = true, unique = false, columnDefinition = "varchar(128)")
	private String address;

	@ManyToOne
	@JoinColumn(name = "CAT_ID", nullable = false, columnDefinition = "char(1)")
	private CustomerCategory category;

	@Column(name = "USR_CUS_POINT", nullable = true, unique = false, columnDefinition = "smallint default 0")
	private int points;
	@Column(name = "USR_CUS_RESERVED", nullable = true, unique = false, columnDefinition = "smallint default 0")
	private int reservedPoints;

	/* PODESI */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
	private Set<Bill> bills = new HashSet<>();

	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public CustomerCategory getCategory() {
		return category;
	}

	public void setCategory(CustomerCategory newCustomerCategory) {
		if (this.category == null || !this.category.equals(newCustomerCategory)) {
			if (this.category != null) {
				CustomerCategory oldCustomerCategory = this.category;
				this.category = null;
				oldCustomerCategory.removeCustomers(this);
			}
			if (newCustomerCategory != null) {
				this.category = newCustomerCategory;
				this.category.addCustomers(this);
			}
		}
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getReservedPoints() {
		return reservedPoints;
	}

	public void setReservedPoints(int reservedPoints) {
		this.reservedPoints = reservedPoints;
	}

	public Set<Bill> getBills() {
		if (bills == null)
			bills = new HashSet<Bill>();
		return bills;
	}

	public Iterator getIteratorBills() {
		if (bills == null)
			bills = new HashSet<Bill>();
		return bills.iterator();
	}

	public void setBills(Set<Bill> newBills) {
		removeAllBills();
		for (Iterator iter = newBills.iterator(); iter.hasNext();)
			addBills((Bill) iter.next());
	}

	public void addBills(Bill newBill) {
		if (newBill == null)
			return;
		if (this.bills == null)
			this.bills = new HashSet<Bill>();
		if (!this.bills.contains(newBill)) {
			this.bills.add(newBill);
			newBill.setCustomer(this);
		}
	}

	public void removeBills(Bill oldBill) {
		if (oldBill == null)
			return;
		if (this.bills != null)
			if (this.bills.contains(oldBill)) {
				this.bills.remove(oldBill);
				oldBill.setCustomer((Customer) null);
			}
	}

	public void removeAllBills() {
		if (bills != null) {
			Bill oldBill;
			for (Iterator iter = getIteratorBills(); iter.hasNext();) {
				oldBill = (Bill) iter.next();
				iter.remove();
				oldBill.setCustomer((Customer) null);
			}
		}
	}

	public Customer(String username, String firstName, String lastName,
			String address, String password) {
		super(username, firstName, lastName, password);
		// TODO Auto-generated constructor stub
		this.address = address;
	}

	public Customer(String username, String firstName, String lastName,
			String password, String address, CustomerCategory category) {
		super(username, firstName, lastName, password);
		this.address = address;
		setCategory(category);
	}
	
	

}
