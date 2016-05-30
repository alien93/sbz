package projara.model.dao.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.model.dao.interfaces.CustomerCategoryDaoLocal;
import projara.model.users.CustomerCategory;

@Stateless
@Local(CustomerCategoryDaoLocal.class)
public class CustomerCategoryDaoBean
		extends
			GenericDaoBean<CustomerCategory, String>
		implements
			CustomerCategoryDaoLocal {

}
