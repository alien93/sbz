package projara.util.json.create;

public class CreateItemCategoryJson {

	private String code;
	private String name;
	private String parentCode;
	private double maxDiscount;
	
	public CreateItemCategoryJson() {
		// TODO Auto-generated constructor stub
	}

	public CreateItemCategoryJson(String code, String name, String parentCode,
			double maxDiscount) {
		super();
		this.code = code;
		this.name = name;
		this.parentCode = parentCode;
		this.maxDiscount = maxDiscount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public double getMaxDiscount() {
		return maxDiscount;
	}

	public void setMaxDiscount(double maxDiscount) {
		this.maxDiscount = maxDiscount;
	}
	
	

}
