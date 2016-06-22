package projara.model.dao.interfaces;

import projara.model.users.CustomerCategory;
import projara.model.users.Threshold;
import projara.rest.manager.ThresholdJson;

public interface CustomerCategoryDaoLocal extends GenericDaoLocal<CustomerCategory, String>{
	
	public Threshold fromJsonThreshold(ThresholdJson json);
}
