package projara.model.dao.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import projara.model.dao.interfaces.ActionEventDaoLocal;
import projara.model.shop.ActionEvent;
@Stateless
@Local(ActionEventDaoLocal.class)
public class ActionEventDaoBean extends GenericDaoBean<ActionEvent, Short>
		implements
			ActionEventDaoLocal {

	@Override
	public List<ActionEvent> findActiveEvents() {
		Query q = em.createNamedQuery("findActiveEvents");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currDate = sdf.format(new Date());
		q.setParameter("currentDate",new Date());
		
		List<ActionEvent> result = null;
		
		try{
			result = q.getResultList();
		}catch(Exception e){e.printStackTrace();}
		
		return result;
	}

}
