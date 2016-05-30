package projara.model.dao.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.model.dao.interfaces.ThresholdDaoLocal;
import projara.model.users.Threshold;

@Stateless
@Local(ThresholdDaoLocal.class)
public class ThresholdDaoBean extends GenericDaoBean<Threshold, Integer>
		implements
			ThresholdDaoLocal {

}
