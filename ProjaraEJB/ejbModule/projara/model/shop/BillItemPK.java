package projara.model.shop;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BillItemPK implements Serializable {

	@Column(name = "BILL_ID",nullable = false)
	int billId;
	@Column(name = "BILLIT_NO", nullable = false)
	int itemNo;
	
	public BillItemPK() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		
		if(!(o instanceof BillItemPK))
			return false;
		
		BillItemPK bk = (BillItemPK)o;
		if(bk.billId == billId && bk.itemNo == itemNo)
			return true;
		
		return false;
	}
	
	@Override
	public int hashCode(){
		String b = new Integer(billId).toString();
		String in = new Integer(itemNo).toString();
		return (b+in).hashCode();
		
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

	public BillItemPK(int bill, int itemNo) {
		super();
		this.billId = bill;
		this.itemNo = itemNo;
	}
	
	
	
	

}
