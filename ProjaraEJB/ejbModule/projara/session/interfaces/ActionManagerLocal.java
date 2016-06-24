package projara.session.interfaces;

import java.util.Date;
import java.util.List;

import projara.model.items.ItemCategory;
import projara.model.shop.ActionEvent;
import projara.util.exception.ActionEventNotExists;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.json.view.ActionJson;

public interface ActionManagerLocal {

	public ActionJson transformAction(ActionEvent ae) throws ActionEventNotExists;

	public ActionJson transformAction(int id) throws ActionEventNotExists;

	public List<ActionJson> transformActions(List<ActionEvent> actions);

	public ActionEvent updateAction(int id,String name, Date from, Date until,
			double dicount) throws ActionEventNotExists;

	public ActionEvent createActionEvent(String name, Date from, Date until,
			double dicount) throws BadArgumentsException;

	public ActionEvent addCategoryToAction(ActionEvent actionEvent,
			ItemCategory itemCategory) throws BadArgumentsException,
			ItemCategoryException;

	public ActionEvent addCategoryToAction(int actionId, String itemCategoryCode)
			throws BadArgumentsException, ItemCategoryException;

	public List<ActionJson> getActionAppliedToSub(List<ActionEvent> actions);

	public ActionJson getActionAppliedToSub(int id) throws ActionEventNotExists;

	public ActionJson getActionAppliedToSub(ActionEvent ae) throws ActionEventNotExists;

	public void removeAction(int id) throws ActionEventNotExists;

	public ActionEvent removeCategoryFromAction(int id, String code)
			throws ActionEventNotExists, ItemCategoryException;

	public ActionEvent removeCategoryFromAction(ActionEvent ae,
			ItemCategory itemCategory) throws ActionEventNotExists,
			ItemCategoryException;
}
