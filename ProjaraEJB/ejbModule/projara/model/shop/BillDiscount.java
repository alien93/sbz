/***********************************************************************
 * Module:  BillDiscount.java
 * Author:  Stanko
 * Purpose: Defines the Class BillDiscount
 ***********************************************************************/

package projara.model.shop;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

/** @pdOid c05e91d1-9a51-46b5-9ffd-f9cf2aa5e690 */
@Entity
@Table(name = "BILL_DISCOUNT")
public class BillDiscount implements Serializable {
	/** @pdOid ebebe7a4-3272-4d93-9d28-0959476fdf4e */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BID_ID", nullable = false, unique = true)
	private int id;
	/** @pdOid f173535d-effc-429f-9787-46929bf49bc5 */
	@Column(name = "BID_DISCOUNT", nullable = true, unique = false, columnDefinition = "numeric(5,2)")
	private double discount;
	/** @pdOid 33b78669-409e-48d7-9b56-a57e1cc68340 */
	@Column(name = "BID_TYPE", nullable = false, unique = false, columnDefinition = "char(1) default 'R'")
	private String type;
	@Column(name ="BID_NAME", nullable = false, unique = false, columnDefinition ="varchar(255)" )
	private String name;

	/** @pdRoleInfo migr=no name=Bill assc=hasDiscount mult=1..1 side=A */
	@ManyToOne
	@JoinColumn(name = "BILL_ID", referencedColumnName = "BILL_ID", nullable = false)
	private Bill bill;

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
				oldBill.removeBillDiscounts(this);
			}
			if (newBill != null) {
				this.bill = newBill;
				this.bill.addBillDiscounts(this);
			}
		}
	}

	/** @pdOid 071248c5-97d4-4116-bc74-2d401f6c6539 */
	public int getId() {
		return id;
	}

	/**
	 * @param newId
	 * @pdOid 7147705d-1bcd-44c6-a2ef-ff3284d52696
	 */
	public void setId(int newId) {
		id = newId;
	}

	/** @pdOid e875966b-f8df-4fb6-aaf1-b185b4841fa4 */
	public double getDiscount() {
		return discount;
	}

	/**
	 * @param newDiscount
	 * @pdOid 812ef6a2-2513-4aec-bffc-235a824fe217
	 */
	public void setDiscount(double newDiscount) {
		discount = newDiscount;
	}

	/** @pdOid 18f53b1d-8d10-4daa-883a-9f7b8f5e0e3d */
	public String getType() {
		return type;
	}

	/**
	 * @param newType
	 * @pdOid 4160db05-bd77-4102-b6e4-87de933d39c6
	 */
	public void setType(String newType) {
		type = newType;
	}

	public BillDiscount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BillDiscount(double discount, String type) {
		super();
		this.discount = discount;
		this.type = type;
	}

	@Override
	public int hashCode() {

		if (id == 0) {
			Random rand = new Random();
			return 31 * rand.nextInt(10000000) + (new Date()).hashCode();
		}

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
		if (!(obj instanceof BillDiscount)) {
			return false;
		}
		BillDiscount other = (BillDiscount) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	public BillDiscount(double discount, String type, Bill bill,String name) {
		super();
		this.discount = discount;
		this.type = type;
		this.name = name;
		setBill(bill);
	}

	/*
	@PreRemove
	public void preRemoveBillDiscount() {
		if (bill != null)
			bill.removeBillDiscounts(this);

	}
	
	*/
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}