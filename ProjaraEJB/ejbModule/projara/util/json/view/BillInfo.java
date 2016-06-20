package projara.util.json.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BillInfo implements Serializable{

	private int billId;
	
	//DOPUNITI
	
	private List<BillCostInfo> costInfos;
	
	public BillInfo() {
		costInfos = new ArrayList<BillCostInfo>();
	}

	public BillInfo(int billId, List<BillCostInfo> costInfos) {
		super();
		this.billId = billId;
		this.costInfos = costInfos;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public List<BillCostInfo> getCostInfos() {
		return costInfos;
	}

	public void setCostInfos(List<BillCostInfo> costInfos) {
		this.costInfos = costInfos;
	}
	
	

}
