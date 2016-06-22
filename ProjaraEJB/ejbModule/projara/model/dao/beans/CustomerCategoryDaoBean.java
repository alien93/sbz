package projara.model.dao.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.model.dao.interfaces.CustomerCategoryDaoLocal;
import projara.model.users.CustomerCategory;
import projara.model.users.Threshold;
import projara.rest.manager.ThresholdJson;

@Stateless
@Local(CustomerCategoryDaoLocal.class)
public class CustomerCategoryDaoBean
		extends
			GenericDaoBean<CustomerCategory, String>
		implements
			CustomerCategoryDaoLocal {
	
	@Override
	public Threshold fromJsonThreshold(ThresholdJson json) {
		Threshold t = new Threshold();
		t.setId(json.getId());
		t.setFrom(json.getFrom());
		t.setTo(json.getTo());
		t.setPercentage(json.getPercentage());
		return t;
	}
}
