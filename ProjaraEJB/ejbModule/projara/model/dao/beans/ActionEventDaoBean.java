package projara.model.dao.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.model.dao.interfaces.ActionEventDaoLocal;
import projara.model.shop.ActionEvent;
@Stateless
@Local(ActionEventDaoLocal.class)
public class ActionEventDaoBean extends GenericDaoBean<ActionEvent, Short>
		implements
			ActionEventDaoLocal {

}
