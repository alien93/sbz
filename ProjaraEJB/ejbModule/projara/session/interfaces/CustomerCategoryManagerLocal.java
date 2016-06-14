package projara.session.interfaces;

import projara.model.users.CustomerCategory;
import projara.model.users.Threshold;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.CustomerCategoryException;

public interface CustomerCategoryManagerLocal {

	public CustomerCategory makeCustomerCategory(String code,String name) throws CustomerCategoryException, BadArgumentsException;
	public CustomerCategory addThreshold(CustomerCategory customerCategory, Threshold threshold) throws CustomerCategoryException;
	public Threshold makeThreshold(double from,double to,double percentage);
	public CustomerCategory addThreshold(String customerCategory,int threshold) throws CustomerCategoryException, BadArgumentsException;
	
}
