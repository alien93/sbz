package projara.model.shop;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class BillItemPK implements Serializable {

	@Column(name = "BILL_ID", nullable = false)
	int billId;
	@Column(name = "BILLIT_NO", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int itemNo;

	public BillItemPK() {
		// TODO Auto-generated constructor stub
	}

	public int getBill() {
		return billId;
	}

	public void setBill(int bill) {
		this.billId = bill;
	}

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public BillItemPK(int billId) {
		super();
		this.billId = billId;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		String str = (new Integer(billId)).toString()
				+ (new Integer(itemNo)).toString();
		int result = prime * str.hashCode();
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
		if (!(obj instanceof BillItemPK)) {
			return false;
		}
		BillItemPK other = (BillItemPK) obj;
		if (billId != other.billId) {
			return false;
		}
		if (itemNo != other.itemNo) {
			return false;
		}
		return true;
	}

}
