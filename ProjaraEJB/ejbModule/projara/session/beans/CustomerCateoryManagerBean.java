package projara.session.beans;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import projara.model.dao.interfaces.CustomerCategoryDaoLocal;
import projara.model.dao.interfaces.ThresholdDaoLocal;
import projara.model.users.CustomerCategory;
import projara.model.users.Threshold;
import projara.session.interfaces.CustomerCategoryManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.CustomerCategoryException;
import projara.util.interceptors.CheckParametersInterceptor;

@Stateless
@Local(CustomerCategoryManagerLocal.class)
public class CustomerCateoryManagerBean implements CustomerCategoryManagerLocal {

	@EJB
	private CustomerCategoryDaoLocal customerCategoryDao;

	@EJB
	private ThresholdDaoLocal thresholdDao;

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public CustomerCategory makeCustomerCategory(String code, String name)
			throws CustomerCategoryException, BadArgumentsException {

		CustomerCategory cc = customerCategoryDao.findById(code);

		if (cc != null)
			throw new CustomerCategoryException("Customer category with code: "
					+ code + " already exists");

		cc = new CustomerCategory(code, name);

		try {
			customerCategoryDao.persist(cc);
		} catch (Exception e) {
			throw new CustomerCategoryException(
					"Problem occured, try again later");
		}

		return cc;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public CustomerCategory addThreshold(CustomerCategory customerCategory,
			Threshold threshold) throws CustomerCategoryException {

		try {
			customerCategory = customerCategoryDao.merge(customerCategory);
			threshold = thresholdDao.merge(threshold);
		} catch (Exception e) {
			throw new CustomerCategoryException(
					"Check if category or threshold exist");
		}

		if (customerCategory.getThresholds().contains(threshold)) {
			throw new CustomerCategoryException("Customer category: "
					+ customerCategory.getName()
					+ " already has threshold with id: " + threshold.getId());
		}

		try {
			customerCategory.addThresholds(threshold);
			customerCategory = customerCategoryDao.persist(customerCategory);
		} catch (Exception e) {
			throw new CustomerCategoryException(
					"Problem occured while persisting customer category, try again");
		}

		return customerCategory;
	}

	@Override
	public Threshold makeThreshold(double from, double to, double percentage) {

		Threshold threshold = new Threshold(from, to, percentage);

		threshold = thresholdDao.persist(threshold);

		return threshold;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public CustomerCategory addThreshold(String customerCategory, int threshold)
			throws CustomerCategoryException, BadArgumentsException {

		CustomerCategory custCat = null;
		Threshold t = null;

		try {
			custCat = customerCategoryDao.findById(customerCategory);
			t = thresholdDao.findById(threshold);
		} catch (Exception e) {
			throw new BadArgumentsException(
					"Couldn't find customer category with code: "
							+ customerCategory + " and threshold with id: "
							+ threshold);
		}
		

		return addThreshold(custCat, t);
	}

}
