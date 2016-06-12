/***********************************************************************
 * Module:  BillItemDiscount.java
 * Author:  Stanko
 * Purpose: Defines the Class BillItemDiscount
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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/** @pdOid c2f9f85e-0f6b-4f23-9a54-a1d250c2462a */
@Entity
@Table(name = "BILL_ITEM_DISCOUNT")
public class BillItemDiscount implements Serializable {
	/** @pdOid bebd456d-f9d9-477b-8913-70fff361b424 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BITD_ID", nullable = false, unique = true)
	private int id;
	/** @pdOid 51f7ab44-fa01-48d6-9751-bd1a354a77ec */
	@Column(name = "BITD_DISCOUNT", nullable = false, unique = false, columnDefinition = "numeric(4,2) default 0")
	private double discount;
	/** @pdOid 1613481f-9e5e-4931-b2cd-eb1fbf07c2ea */
	@Column(name = "BITD_TYPE", nullable = false, unique = false, columnDefinition = "char(1) default 'R'")
	private String type;

	/** @pdRoleInfo migr=no name=BillItem assc=itemHasDiscounts mult=1..1 side=A */
	@ManyToOne
	@JoinColumns(value = {
			@JoinColumn(name = "BILL_ID", referencedColumnName = "BILL_ID"),
			@JoinColumn(name = "BILLIT_NO", referencedColumnName = "BILLIT_NO")})
	private BillItem billItem;

	/** @pdGenerated default parent getter */
	public BillItem getBillItem() {
		return billItem;
	}

	/**
	 * @pdGenerated default parent setter
	 * @param newBillItem
	 */
	public void setBillItem(BillItem newBillItem) {
		if (this.billItem == null || !this.billItem.equals(newBillItem)) {
			if (this.billItem != null) {
				BillItem oldBillItem = this.billItem;
				this.billItem = null;
				oldBillItem.removeDiscounts(this);
			}
			if (newBillItem != null) {
				this.billItem = newBillItem;
				this.billItem.addDiscounts(this);
			}
		}
	}

	/** @pdOid f5575ec8-63a6-4609-b628-a81aa488310b */
	public int getId() {
		return id;
	}

	/**
	 * @param newId
	 * @pdOid c4534eb4-c79f-4329-99cc-71e1c1908779
	 */
	public void setId(int newId) {
		id = newId;
	}

	/** @pdOid ee54875a-45ae-45eb-88a1-d89b7471f593 */
	public double getDiscount() {
		return discount;
	}

	/**
	 * @param newDiscount
	 * @pdOid 835fb1ac-7e5b-4196-b95c-33ae37921858
	 */
	public void setDiscount(double newDiscount) {
		discount = newDiscount;
	}

	/** @pdOid ac813135-57b5-4227-9f10-d8b146f06609 */
	public String getType() {
		return type;
	}

	/**
	 * @param newType
	 * @pdOid ec3d5cbd-3020-46a0-94a6-a97dec19458d
	 */
	public void setType(String newType) {
		type = newType;
	}

	public BillItemDiscount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BillItemDiscount(double discount, String type) {
		super();
		this.discount = discount;
		this.type = type;
	}

	@Override
	public int hashCode() {
		if(id == 0){
			Random rand = new Random();
			return 31*rand.nextInt(10000000)+(new Date()).hashCode();
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
		if (!(obj instanceof BillItemDiscount)) {
			return false;
		}
		BillItemDiscount other = (BillItemDiscount) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	public BillItemDiscount(double discount, String type, BillItem billItem) {
		super();
		this.discount = discount;
		this.type = type;
		setBillItem(billItem);
	}
	
	

}