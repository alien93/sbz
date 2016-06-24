package projara.session.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import projara.model.dao.interfaces.ActionEventDaoLocal;
import projara.model.dao.interfaces.ItemCategoryDaoLocal;
import projara.model.items.ItemCategory;
import projara.model.shop.ActionEvent;
import projara.session.interfaces.ActionManagerLocal;
import projara.util.exception.ActionEventNotExists;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemCategoryNotExistsException;
import projara.util.interceptors.CheckParametersInterceptor;
import projara.util.json.view.ActionInfo;
import projara.util.json.view.ActionJson;
import projara.util.json.view.ItemCategoryInfo;

@Stateless
@Local(ActionManagerLocal.class)
public class ActionManagerBean implements ActionManagerLocal {

	@EJB
	private ActionEventDaoLocal actionEventDao;

	@EJB
	private ItemCategoryDaoLocal itemCategoryDao;

	@Override
	public ActionJson transformAction(ActionEvent ae)
			throws ActionEventNotExists {
		try {
			ae = actionEventDao.merge(ae);
		} catch (Exception e) {
			throw new ActionEventNotExists("Action event not exists");
		}

		ActionJson json = new ActionJson();
		ActionInfo ainfo = new ActionInfo(ae.getId(), ae.getName(),
				ae.getFrom(), ae.getUntil(), ae.getDiscount());

		List<ItemCategoryInfo> categories = new ArrayList<ItemCategoryInfo>();
		for (ItemCategory ic : ae.getCategories()) {
			ItemCategoryInfo ici = new ItemCategoryInfo(ic.getCode(),
					ic.getName(), ic.getMaxDiscount());
			categories.add(ici);
		}

		json.setInfo(ainfo);
		json.setCategories(categories);

		return json;
	}

	@Override
	public ActionJson transformAction(int id) throws ActionEventNotExists {

		ActionEvent ae = null;
		try {
			ae = actionEventDao.findById(id);
		} catch (Exception e) {
			throw new ActionEventNotExists("Action event not exists with id: "
					+ id);
		}
		if (ae == null)
			throw new ActionEventNotExists("Action event not exists with id: "
					+ id);

		return transformAction(ae);
	}

	@Override
	public List<ActionJson> transformActions(List<ActionEvent> actions) {
		List<ActionJson> actionsJson = new ArrayList<>();

		for (ActionEvent ae : actions) {
			try {
				actionsJson.add(transformAction(ae));
			} catch (ActionEventNotExists e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return actionsJson;
	}

	@Override
	@Transactional(value = TxType.REQUIRES_NEW, rollbackOn = {ActionEventNotExists.class})
	public ActionEvent updateAction(int id, String name, Date from, Date until,
			double dicount) throws ActionEventNotExists {

		ActionEvent ae = null;
		try {
			ae = actionEventDao.findById(id);
		} catch (Exception e) {
			throw new ActionEventNotExists("Action event not exists with id: "
					+ id);
		}
		if (ae == null)
			throw new ActionEventNotExists("Action event not exists with id: "
					+ id);

		name = name == null ? "" : name.trim();

		if (!name.isEmpty() && !ae.getName().equals(name))
			ae.setName(name);

		if (from != null && until != null && from.before(until)) {
			ae.setFrom(from);
			ae.setUntil(until);
		}

		if (dicount != ae.getDiscount())
			ae.setDiscount(dicount);

		try {
			ae = actionEventDao.persist(ae);
		} catch (Exception e) {
		}

		return ae;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	@Transactional(rollbackOn = {BadArgumentsException.class}, value = TxType.REQUIRES_NEW)
	public ActionEvent createActionEvent(String name, Date from, Date until,
			double dicount) throws BadArgumentsException {

		if (from.after(until))
			throw new BadArgumentsException(
					"date from is greater then date until");

		ActionEvent actionEvent = new ActionEvent(name, from, until, dicount);

		try {
			actionEvent = actionEventDao.persist(actionEvent);
		} catch (Exception e) {
			throw new BadArgumentsException("Cant create action event");
		}

		return actionEvent;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	@Transactional(value = TxType.REQUIRED, rollbackOn = {
			BadArgumentsException.class, ItemCategoryException.class})
	public ActionEvent addCategoryToAction(ActionEvent actionEvent,
			ItemCategory itemCategory) throws BadArgumentsException,
			ItemCategoryException {

		try {
			actionEvent = actionEventDao.merge(actionEvent);
			itemCategory = itemCategoryDao.merge(itemCategory);
		} catch (Exception e) {
			throw new BadArgumentsException(
					"Action or item category not exists");
		}

		actionEvent.addCategories(itemCategory);

		try {
			actionEvent = actionEventDao.persist(actionEvent);
			itemCategory = itemCategoryDao.persist(itemCategory);
		} catch (Exception e) {
			throw new ItemCategoryException(
					"Error while persisting action event and itemCategoty");
		}

		return actionEvent;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	@Transactional(value = TxType.REQUIRES_NEW, rollbackOn = {
			BadArgumentsException.class, ItemCategoryException.class})
	public ActionEvent addCategoryToAction(int actionId, String itemCategoryCode)
			throws BadArgumentsException, ItemCategoryException {

		ItemCategory ic = null;
		ActionEvent ae = null;

		try {
			ic = itemCategoryDao.findById(itemCategoryCode);
			ae = actionEventDao.findById(actionId);
		} catch (Exception e) {
			throw new BadArgumentsException();
		}
		if (ae == null || ic == null)
			throw new BadArgumentsException(
					"action event and item category are null");

		return addCategoryToAction(ae, ic);
	}

	@Override
	public List<ActionJson> getActionAppliedToSub(List<ActionEvent> actions) {
		List<ActionJson> retVal = new ArrayList<>();
		if(actions == null || actions.isEmpty())
			return retVal;
		
		for (ActionEvent ae : actions) {
			try {
				retVal.add(getActionAppliedToSub(ae));
			} catch (ActionEventNotExists e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return retVal;
	}
	@Override
	public ActionJson getActionAppliedToSub(int id) throws ActionEventNotExists {
		ActionEvent ae = null;
		try {
			ae = actionEventDao.findById(id);
		} catch (Exception e) {
			throw new ActionEventNotExists("Action event not exists with id: "
					+ id);
		}
		if (ae == null)
			throw new ActionEventNotExists("Action event not exists with id: "
					+ id);
		return getActionAppliedToSub(ae);
	}

	@Override
	public ActionJson getActionAppliedToSub(ActionEvent ae) throws ActionEventNotExists {
		
		try {
			ae = actionEventDao.merge(ae);
		} catch (Exception e) {
			throw new ActionEventNotExists("Action event not exists");
		}
		
		ActionJson json = new ActionJson();
		ActionInfo ai = new ActionInfo(ae.getId(), ae.getName(), ae.getFrom(),
				ae.getUntil(), ae.getDiscount());

		List<ItemCategoryInfo> categories = new ArrayList<>();
		for (ItemCategory ic : ae.getCategories()) {

			ArrayList<ItemCategory> queue = new ArrayList<>();
			queue.add(ic);
			while (!queue.isEmpty()) {

				ItemCategory current = queue.remove(0);
				ItemCategoryInfo ici = new ItemCategoryInfo(current.getCode(),
						current.getName(), current.getMaxDiscount());
				categories.add(ici);
				for(ItemCategory subcat:current.getSubCategories()){
					queue.add(subcat);
				}

			}

		}
		json.setInfo(ai);
		json.setCategories(categories);

		return json;
	}

	@Override
	@Transactional(value = TxType.REQUIRES_NEW)
	public void removeAction(int id) throws ActionEventNotExists {
		ActionEvent ae = null;
		try {
			ae = actionEventDao.findById(id);
		} catch (Exception e) {
			throw new ActionEventNotExists("Action event not exists with id: "
					+ id);
		}
		if (ae == null)
			throw new ActionEventNotExists("Action event not exists with id: "
					+ id);
		ae.removeAllCategories();
		actionEventDao.remove(ae);
		actionEventDao.flush();

	}

	@Override
	@Transactional(value = TxType.REQUIRES_NEW)
	public ActionEvent removeCategoryFromAction(int id, String code)
			throws ActionEventNotExists, ItemCategoryException {
		ActionEvent ae = null;
		ItemCategory ic = null;

		try {
			ae = actionEventDao.findById(id);
		} catch (Exception e) {
			throw new ActionEventNotExists("Action event not exists with id: "
					+ id);
		}
		if (ae == null)
			throw new ActionEventNotExists("Action event not exists with id: "
					+ id);

		try {
			ic = itemCategoryDao.findById(code);
		} catch (Exception e) {
			throw new ItemCategoryNotExistsException(
					"Item category do not exists with code: " + code);
		}
		if (ic == null)
			throw new ItemCategoryNotExistsException(
					"Item category do not exists with code: " + code);

		return removeCategoryFromAction(ae, ic);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public ActionEvent removeCategoryFromAction(ActionEvent ae,
			ItemCategory itemCategory) throws ActionEventNotExists,
			ItemCategoryException {

		try {
			ae = actionEventDao.merge(ae);
		} catch (Exception e) {
			throw new ActionEventNotExists("Action event not exists");
		}

		try {
			itemCategory = itemCategoryDao.merge(itemCategory);
		} catch (Exception e) {
			throw new ItemCategoryNotExistsException(
					"Item category do not exists");
		}

		ae.removeCategories(itemCategory);
		return ae;
	}

}
